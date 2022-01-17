package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.BrandEntity;
import com.kkb.cubemall.product.entity.CategoryBrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 分类品牌关系表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 17:17:06
 */
public interface CategoryBrandService extends IService<CategoryBrandEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<CategoryBrandEntity> getCategoryBrands(QueryWrapper<CategoryBrandEntity> brand_id);

    void removeCategoryBrandEntity(QueryWrapper<CategoryBrandEntity> queryWrapper);

    List<BrandEntity> getBrandsByCategoryId(Integer categoryId);
}

