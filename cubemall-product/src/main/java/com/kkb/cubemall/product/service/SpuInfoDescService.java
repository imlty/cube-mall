package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu描述
 *
 * @author imlty
 * @email imlty626@qq.com
 * @date 2021-09-08 00:57:02
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

