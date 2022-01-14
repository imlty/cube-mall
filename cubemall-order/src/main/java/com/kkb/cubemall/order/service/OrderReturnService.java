package com.kkb.cubemall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.order.entity.OrderReturnEntity;

import java.util.Map;

/**
 * 订单退货申请
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:53
 */
public interface OrderReturnService extends IService<OrderReturnEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

