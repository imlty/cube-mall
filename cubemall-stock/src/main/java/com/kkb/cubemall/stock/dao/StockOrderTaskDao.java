package com.kkb.cubemall.stock.dao;

import com.kkb.cubemall.stock.entity.StockOrderTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存工作单
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:10
 */
@Mapper
public interface StockOrderTaskDao extends BaseMapper<StockOrderTaskEntity> {
	
}
