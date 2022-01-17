package com.kkb.cubemall.product.vo;

import com.kkb.cubemall.product.entity.SkuImagesEntity;
import com.kkb.cubemall.product.entity.SkuInfoEntity;
import com.kkb.cubemall.product.entity.SpuInfoDescEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName SkuItemVo
 * @Description
 * @Author hubin
 * @Date 2021/5/15 16:14
 * @Version V1.0
 **/
@ToString
@Data
public class SkuItemVo {

    // 1、 sku基本信息
    private SkuInfoEntity info;

    // 2、sku图片信息
    private List<SkuImagesEntity> images;

    //3、spu的销售属性组合
    private List<SkuItemSaleAttrVo> attrSales;

    //4、spu描述信息
    private SpuInfoDescEntity desc;

    //5、spu分组（主体，基本信息...）规格属性
    private List<SpuAttrGroupVo> attrGroups;


    //6、商品的秒杀信息
    private SeckillSkuVo seckillSkuVo;
}

