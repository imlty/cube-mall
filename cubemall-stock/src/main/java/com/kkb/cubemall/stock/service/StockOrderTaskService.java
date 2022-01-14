package com.kkb.cubemall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.stock.entity.StockOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:10
 */
public interface StockOrderTaskService extends IService<StockOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

