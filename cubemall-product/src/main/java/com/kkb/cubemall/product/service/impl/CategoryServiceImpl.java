package com.kkb.cubemall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.dao.CategoryDao;
import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //1.查询所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2.组装成父子的树形结构
        //2.1 找到所有的一级分类
        List<CategoryEntity> levelOneMenus = entities.stream().filter(
                //过滤过一级分类 parentId==0, 根据这个条件构建出所有一级分类的数据
                categoryEntity -> categoryEntity.getParentId() == 0
        ).map((menu) -> {
            //出现递归操作,关联出子分类(2,3级分类)
            menu.setChildrens(getChildrens(menu, entities));
            return menu;
        }).collect(Collectors.toList());

        return levelOneMenus;
    }

    /**
     * 逻辑删除菜单
     *
     * @param asList
     */
    @Override
    public void removeMenuByIds(List<Integer> asList) {
        //TODO 检查当前要删除的菜单是否被别的地方引用
        //逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 收集三级菜单id
     *
     * @param categoryId
     * @return [558, 559, 560]
     */
    @Override
    public Long[] findCategoryPath(Integer categoryId) {
        List<Long> paths = new ArrayList<>();
        //通过递归查询到 把当前分类id与 父分类id 添加到 paths集合中
        List<Long> parentPath = findParentPath(categoryId, paths);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 改造：用 redis 缓存热点数据
     *
     * @return
     */
    @Override
    public List<CategoryEntity> getLevel1Categories() {

        // 使用分布式锁，redis

        // 标识分布式锁
        String lock = UUID.randomUUID().toString();

        // 1. 占据锁 [设置过期时间，防止未删除锁而赵成的 BUG ]
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent("lock", lock, 30, TimeUnit.SECONDS);
        if (isLock) {
            List<CategoryEntity> dataFromDb;
            try {
                dataFromDb = getDataFromDb();
            } finally {
                // 2. 删除属于自己的锁；防止误删除其他锁
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                redisTemplate.execute(new DefaultRedisScript<Integer>(script, Integer.class), Arrays.asList("lock"), lock);
            }
            return dataFromDb;
        } else {
            // 加锁失败  自旋
            return getLevel1Categories();
        }
    }

    private List<CategoryEntity> getDataFromDb() {
        String categoryJSON = redisTemplate.opsForValue().get("categoryJSON"); // 从缓存中读

        if (StringUtils.isEmpty(categoryJSON)) {
            System.out.println("缓存未命中，准备查询数据库...");
            // 从数据库查询
            List<CategoryEntity> tree = listWithTree();

            // 缓存进 redis
            String treeJSON = JSON.toJSONString(tree);
            System.out.println("已从数据库中读取...");
            redisTemplate.opsForValue().set("categoryJSON", treeJSON, 1, TimeUnit.DAYS);
            System.out.println("已缓存热点数据...");
            return tree;
        } else {
            System.out.println("缓存命中...");
            return JSON.parseArray(categoryJSON, CategoryEntity.class);
        }
    }

    /**
     * 递归收集菜单id
     *
     * @param categoryId
     * @param paths
     * @return [560, 559, 558]
     */
    private List<Long> findParentPath(Integer categoryId, List<Long> paths) {
        //收集当前分类id到集合中
        paths.add(categoryId.longValue());

        CategoryEntity categoryEntity = this.getById(categoryId);
        if (categoryEntity.getParentId() != 0) {
            findParentPath(categoryEntity.getParentId(), paths);
        }
        return paths;
    }


    /**
     * 递归查找指定分类的所有子分类( 所有菜单的子菜单)
     *
     * @param currentMenu
     * @param entities
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity currentMenu, List<CategoryEntity> entities) {
        List<CategoryEntity> childrens = entities.stream()
                .filter(
                        //过滤出 当前菜单的所有匹配的子菜单 currentMenu.id == categoryEntity.parentId
                        categoryEntity -> currentMenu.getId().equals(categoryEntity.getParentId())
                )
                .map((menu) -> {
                    //找到子分类
                    menu.setChildrens(getChildrens(menu, entities));
                    return menu;
                }).collect(Collectors.toList());

        return childrens;
    }

}