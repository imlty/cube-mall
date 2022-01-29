package com.kkb.cubemall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.product.dao.CategoryDao;
import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.CategoryService;


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

    @Override
    public List<CategoryEntity> listTree() {
        //获取所有的菜单
        //
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        //获取分类的一级菜单
        List<CategoryEntity> firstLevelMenus = categoryEntities.stream().filter(categoryEntity ->
            categoryEntity.getParentId() == 0 //如果父节点ID等于0 就是一级菜单
        ).map(menu->{
            menu.setChildrens(getChildrens(menu,categoryEntities)); //设置当前节点子节点
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSeq()==null?0:menu1.getSeq())-(menu2.getSeq()==null?0:menu2.getSeq());
        }).collect(Collectors.toList());
        return firstLevelMenus;
    }

    /**
     * 批量删除(逻辑)
     * @param asList
     */
    @Override
    public void removeNodesByIds(List<Integer> asList) {
        baseMapper.deleteBatchIds(asList);
    }


    /**
     * 根据子节点id，获取节点路径(父类节点ID)
     * @param categoryId
     * @return
     */
    @Override
    public Integer[] findCategoryPath(Integer categoryId) {
        List<Integer> paths = new ArrayList<>();
        List<Integer> parentPath = this.findParentPath(categoryId,paths);
        //颠倒顺序
        Collections.reverse(parentPath);
        return parentPath.toArray(new Integer[parentPath.size()]);
    }

    /**
     * 查询父节点ID
     * id 当前节点ID
     * @return
     */
    private List<Integer> findParentPath(Integer id,List<Integer> paths){
        paths.add(id);
        //根据id查询当前bean对象
        CategoryEntity categoryEntity = this.getById(id);
        if(categoryEntity.getParentId()!=0){ //如果不为0，表示有父节点
            findParentPath(categoryEntity.getParentId(),paths);
        }
        //返回包含节点id的集合
        return paths;
    }

    /**
     * 获取当前节点的子节点
     * @param first
     * @param all
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity first,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentId().equals(first.getId()); //当前节点ID等于子节点的父ID
        }).map(categoryEntity -> {
            categoryEntity.setChildrens(getChildrens(categoryEntity,all)); //递归查询
            return categoryEntity;
        }).collect(Collectors.toList());

        return children;
    }



}
