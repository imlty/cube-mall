package com.kkb.cubemall.product.dao;

import com.kkb.cubemall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kkb.cubemall.product.vo.GroupAttrParamVo;
import com.kkb.cubemall.product.vo.SpuAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性分组表
 * 
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 14:04:42
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {
    //根据spuID,categoryId 查询 sku分组规格参数属性值
    public List<SpuAttrGroupVo> getGroupAttr(GroupAttrParamVo paramVo);

}
