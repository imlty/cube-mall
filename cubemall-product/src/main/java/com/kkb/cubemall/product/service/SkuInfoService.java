package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku??Ϣ
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

