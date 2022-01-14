package com.kkb.cubemall.order.dao;

import com.kkb.cubemall.order.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:53
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
