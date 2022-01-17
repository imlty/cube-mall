package com.kkb.cubemall.product.dao;

import com.kkb.cubemall.product.entity.CategoryBrandEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类品牌关系表
 * 
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 17:17:06
 */
@Mapper
public interface CategoryBrandDao extends BaseMapper<CategoryBrandEntity> {
	
}
