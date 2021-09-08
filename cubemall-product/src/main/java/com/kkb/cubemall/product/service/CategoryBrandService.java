package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.CategoryBrandEntity;

import java.util.Map;

/**
 * 分类品牌关系表
 *
 * @author imlty
 * @email imlty626@qq.com
 * @date 2021-09-08 00:57:02
 */
public interface CategoryBrandService extends IService<CategoryBrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

