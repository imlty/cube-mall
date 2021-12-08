package com.kkb.cubemall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.kkb.cubemall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo {
    /**
     * 自增ID
     */
    private Long id;
    /**
     * 名称
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
     * 图表
     */
    private String icon;
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 当前属性分组关联的属性集
     */
    private List<AttrEntity> attrs;
}
