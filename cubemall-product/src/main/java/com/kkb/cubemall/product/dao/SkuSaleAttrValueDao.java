package com.kkb.cubemall.product.dao;

import com.kkb.cubemall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kkb.cubemall.product.vo.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sku销售属性值
 * 
 * @author hubin
 * @email ithubin@163.com
 * @date 2021-05-14
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    public List<SkuItemSaleAttrVo> getSaleAttrs(Long spuId);

    public List<String> getSaleAttrsString(Long skuId);
}
