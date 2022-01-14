package com.kkb.cubemall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.stock.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:10
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

