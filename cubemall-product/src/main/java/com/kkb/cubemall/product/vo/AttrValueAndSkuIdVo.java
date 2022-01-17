package com.kkb.cubemall.product.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName AttrValueAndSkuIdVo
 * @Description
 * @Author hubin
 * @Date 2021/5/15 16:19
 * @Version V1.0
 **/
@ToString
@Data
public class AttrValueAndSkuIdVo {

    // skuids组合id
    private String skuIds;

    // 属性值：白色，128G
    private String attrValue;

    // 白色： [3,6]
    //128G： [3,9,8]

}

