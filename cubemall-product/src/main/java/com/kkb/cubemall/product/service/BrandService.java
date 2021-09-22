package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌表
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

