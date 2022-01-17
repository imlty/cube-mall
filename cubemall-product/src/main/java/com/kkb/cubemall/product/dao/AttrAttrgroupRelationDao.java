package com.kkb.cubemall.product.dao;

import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组关联表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 14:04:42
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
