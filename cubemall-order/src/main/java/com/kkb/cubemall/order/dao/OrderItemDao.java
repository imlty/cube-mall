package com.kkb.cubemall.order.dao;

import com.kkb.cubemall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:54
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
