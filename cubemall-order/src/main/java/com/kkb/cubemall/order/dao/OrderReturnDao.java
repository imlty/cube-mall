package com.kkb.cubemall.order.dao;

import com.kkb.cubemall.order.entity.OrderReturnEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单退货申请
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:53
 */
@Mapper
public interface OrderReturnDao extends BaseMapper<OrderReturnEntity> {
	
}
