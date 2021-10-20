package com.kkb.cubemall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;
import com.kkb.cubemall.product.dao.CategoryDao;
import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询所有分类[包括分类的所有子分类（优势为：只查询了一次数据库）]
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // 1. 查询
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        // 2. 组装 [子父级关系]
        // 一级分类
        List<CategoryEntity> levelOneMenus = categoryEntities
                .stream()
                .filter(entity -> entity.getParentId() == 0)
                .peek(menu -> {
                    // 递归操作，关联出子分类（2，3级分类）找每个一级菜单的子菜单
                    menu.setChildren(getChildren(menu, categoryEntities));
                })
                .collect(Collectors.toList());
        return levelOneMenus;
    }

    @Override
    public void removeMenuByIds(List<Integer> asList) {
        // TODO: 检查当前要删除的菜单是否被别的地方所引用
        //
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 收集三级菜单Id
     *
     * @param categoryId
     * @return
     */
    @Override
    public Long[] findCategoryPath(Integer categoryId) {
        List<Long> path = new ArrayList<>();
        // 通过递归查询到当前分类 id 与 父分类 id 添加到 path 集合中
        List<Long> parentPath = findParentPath(categoryId, path);
        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 递归收集菜单id
     *
     * @param categoryId
     * @param path
     * @return
     */
    private List<Long> findParentPath(Integer categoryId, List<Long> path) {
        path.add(categoryId.longValue());
        CategoryEntity categoryEntity = this.getById(categoryId);
        if (categoryEntity.getParentId() != 0) {
            findParentPath(categoryEntity.getParentId(), path);
        }

        return path;
    }

    /**
     * 递归查找指定分类的所有子分类（ 所有菜单的子菜单 ）
     *
     * @param currentMenu
     * @param categoryEntities
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity currentMenu, List<CategoryEntity> categoryEntities) {
        List<CategoryEntity> children = categoryEntities
                .stream()
                .filter(
                        // 过滤出当前菜单所有匹配的子菜单
                        categoryEntity -> Objects.equals(currentMenu.getId(), categoryEntity.getParentId())
                )
                .peek(menu -> menu.setChildren(getChildren(menu, categoryEntities)))
                .collect(Collectors.toList());
        return children;
    }

}