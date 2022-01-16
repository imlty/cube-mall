package com.kkb.cubemall.auth.dao;

import com.kkb.cubemall.auth.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:54
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
