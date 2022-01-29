package com.kkb.cubemall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 场次商品关联表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-05-31 11:02:46
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

