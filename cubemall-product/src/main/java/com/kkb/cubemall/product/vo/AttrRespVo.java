package com.kkb.cubemall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo {

    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 属性分组名称
     */
    private String groupName;
    /**
     * 分类路径
     */
    private Long[] categoryPath;
}
