package com.kkb.cubemall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.io.PipedReader;
import java.util.List;

/**
 * @ClassName SkuItemSaleAttrVo
 * @Description
 * @Author hubin
 * @Date 2021/5/15 16:18
 * @Version V1.0
 **/
@ToString
@Data
public class SkuItemSaleAttrVo {

    private Long attrId;
    private String attrName;
    // 属性值
    private List<AttrValueAndSkuIdVo> attrValues;




}

