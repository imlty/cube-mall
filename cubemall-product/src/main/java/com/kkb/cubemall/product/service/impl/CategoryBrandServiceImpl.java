package com.kkb.cubemall.product.service.impl;

import com.kkb.cubemall.product.dao.BrandDao;
import com.kkb.cubemall.product.dao.CategoryDao;
import com.kkb.cubemall.product.entity.BrandEntity;
import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.product.dao.CategoryBrandDao;
import com.kkb.cubemall.product.entity.CategoryBrandEntity;
import com.kkb.cubemall.product.service.CategoryBrandService;


@Service("categoryBrandService")
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandDao, CategoryBrandEntity> implements CategoryBrandService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryBrandDao categoryBrandDao;

    @Autowired
    BrandService brandService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandEntity> page = this.page(
                new Query<CategoryBrandEntity>().getPage(params),
                new QueryWrapper<CategoryBrandEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取品牌分类关系（包含品牌名称、分类名称）
     * @param brand_id
     * @return
     */
    @Override
    public List<CategoryBrandEntity> getCategoryBrands(QueryWrapper<CategoryBrandEntity> brand_id) {
        List<CategoryBrandEntity> list = this.list(brand_id);
        for(CategoryBrandEntity categoryBrandEntity:list){
            Integer brandId = categoryBrandEntity.getBrandId();
            Integer categoryId = categoryBrandEntity.getCategoryId();
            CategoryEntity categoryEntity = categoryDao.selectById(categoryId);
            BrandEntity brandEntity = brandDao.selectById(brandId);
            categoryBrandEntity.setBrandName(brandEntity.getName());
            categoryBrandEntity.setCategoryName(categoryEntity.getName());
        }

        return list;
    }

    @Override
    public void removeCategoryBrandEntity(QueryWrapper<CategoryBrandEntity> queryWrapper) {
        this.remove(queryWrapper);
    }

    //根据分类ID查询品牌列表
    @Override
    public List<BrandEntity> getBrandsByCategoryId(Integer categoryId) {
        //根据分类id查询分类与品牌的对应关系
        List<CategoryBrandEntity> categoryBrandEntities =
                categoryBrandDao.selectList(new QueryWrapper<CategoryBrandEntity>().eq("category_id",categoryId));
        List<BrandEntity> collect = categoryBrandEntities.stream().map(item->{
                Integer brandId = item.getBrandId();
                BrandEntity brandEntity = brandService.getById(brandId);
                return brandEntity; }
        ).collect(Collectors.toList());
        return collect;
    }


}
