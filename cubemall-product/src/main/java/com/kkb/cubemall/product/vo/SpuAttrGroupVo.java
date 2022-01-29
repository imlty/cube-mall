package com.kkb.cubemall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName SpuAttrGroupVo
 * @Description
 * @Author hubin
 * @Date 2021/5/15 16:24
 * @Version V1.0
 **/
@ToString
@Data
public class SpuAttrGroupVo {

    private String groupName;

    // 属性参数
    private List<Attr> attrs;


}

