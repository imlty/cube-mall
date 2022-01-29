package com.kkb.cubemall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.stock.entity.StockOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:09
 */
public interface StockOrderTaskDetailService extends IService<StockOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

