package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.BrandEntity;

import java.util.Map;

/**
 * ๅ็่กจ
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-07 11:38:21
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

