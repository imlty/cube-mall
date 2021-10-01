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

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * 查询所有分类
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // 1. 查询
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        // 2. 组装 [子父级关系]
        // 一级分类
        List<CategoryEntity> levelOneMenus = categoryEntities.stream().filter(
                        entity -> entity.getParentId() == 0
                )
                .peek(menu -> {
                    // 递归操作，关联出子分类（2，3级分类）找每个一级菜单的子菜单
                    menu.setChildren(getChildren(menu, categoryEntities));
                })

                .collect(Collectors.toList());
        // 二级分类
        // 三级分类
        return levelOneMenus;
    }

    /**
     * 递归差找指定分类的所有子分类（ 所有菜单的子菜单 ）
     *
     * @param currrenMenu
     * @param categoryEntities
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity currrenMenu, List<CategoryEntity> categoryEntities) {
        List<CategoryEntity> children = categoryEntities.stream().filter(
                        // 过滤出当前菜单所有匹配的子菜单
                        categoryEntity -> Objects.equals(currrenMenu.getId(), categoryEntity.getParentId())
                )
                .peek(menu -> menu.setChildren(getChildren(menu, categoryEntities)))
                .collect(Collectors.toList());
        return children;
    }

}