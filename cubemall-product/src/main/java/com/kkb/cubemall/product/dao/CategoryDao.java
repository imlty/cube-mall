package com.kkb.cubemall.product.dao;

import com.kkb.cubemall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品类目
 * 
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-06 10:32:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
