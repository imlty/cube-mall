package com.kkb.cubemall.search.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kkb.cubemall.search.entity.SpuInfoEntity;
import com.kkb.cubemall.search.model.SpuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * spu信息
 * 
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-13 20:26:25
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {
	List<SpuInfo> getSpuInfoList();
	SpuInfo getSpuById(Long id);
}
