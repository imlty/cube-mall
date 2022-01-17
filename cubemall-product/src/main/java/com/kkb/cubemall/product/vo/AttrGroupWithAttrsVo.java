package com.kkb.cubemall.product.vo;

import com.kkb.cubemall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo {

    /**
     * 分组id
     */
    private Long id;
    /**
     * 组名
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long categoryId;


    private List<AttrEntity> attrs;
}
