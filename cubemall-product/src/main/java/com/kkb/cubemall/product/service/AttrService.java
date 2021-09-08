package com.kkb.cubemall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.common.utils.PageUtils;
import com.kkb.cubemall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 属性表
 *
 * @author imlty
 * @email imlty626@qq.com
 * @date 2021-09-08 00:57:02
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

