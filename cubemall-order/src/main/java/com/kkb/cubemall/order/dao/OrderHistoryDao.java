package com.kkb.cubemall.order.dao;

import com.kkb.cubemall.order.entity.OrderHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:53
 */
@Mapper
public interface OrderHistoryDao extends BaseMapper<OrderHistoryEntity> {
	
}
