package com.kkb.cubemall.search.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kkb.cubemall.search.entity.SpuInfoEntity;
import com.kkb.cubemall.search.model.SpuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu信息
 * 
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    /**
     *  获取 spu 信息
     * @param spuId spuId
     * @return SpuInfo
     */
    SpuInfo getSpuInfoById(@Param("spuId") Long spuId);

    /**
     *  查询所有 spu 信息
     * @return List<SpuInfo>
     */
    List<SpuInfo> getSpuInfos();
}
