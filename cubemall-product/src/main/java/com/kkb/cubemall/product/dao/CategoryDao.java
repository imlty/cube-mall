package com.kkb.cubemall.product.dao;

import com.kkb.cubemall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品类目
 * 
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
