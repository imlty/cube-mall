package com.kkb.cubemall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 商品类目
 * 
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
@Data
@TableName("tb_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 子分类
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist=false)
	private List<CategoryEntity> childrens;

	/**
	 * 分类ID
	 */
	@TableId
	private Integer id;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 商品数量
	 */
	private Integer goodsNum;
	/**
	 * 是否显示
	 */
	@TableLogic(value="1", delval = "0")
	private String isShow;
	/**
	 * 是否导航
	 */
	private String isMenu;
	/**
	 * 排序
	 */
	private Integer seq;
	/**
	 * 上级ID
	 */
	private Integer parentId;
	/**
	 * 模板ID
	 */
	private Integer templateId;

}
