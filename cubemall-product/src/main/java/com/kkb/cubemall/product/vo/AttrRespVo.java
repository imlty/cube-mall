package com.kkb.cubemall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo {

    private String categoryName;//分类名称

    private String groupName;

    private Integer[] categoryPath;
}
