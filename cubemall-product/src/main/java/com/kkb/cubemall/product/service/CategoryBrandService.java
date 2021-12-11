package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.BrandEntity;
import com.kkb.cubemall.product.entity.CategoryBrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 分类品牌关系表
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
public interface CategoryBrandService extends IService<CategoryBrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BrandEntity> getBrandByCategoryId(Integer categoryId);
}

