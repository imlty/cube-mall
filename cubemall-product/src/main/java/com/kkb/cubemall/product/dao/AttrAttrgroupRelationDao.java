package com.kkb.cubemall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kkb.cubemall.product.entity.AttrAttrgroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组关联表
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    /**
     * 批量删除
     * @param relationEntities
     */
    void deleteBatch(@Param("entities") List<AttrAttrgroupRelationEntity> relationEntities);
}
