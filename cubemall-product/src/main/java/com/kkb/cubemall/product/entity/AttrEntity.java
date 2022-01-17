package com.kkb.cubemall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 属性表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-09 14:04:42
 */
@Data
@TableName("tb_attr")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 搜索类型
	 */
	private Integer searchType;
	/**
	 * 图表
	 */
	private String icon;
	/**
	 * 选择值
	 */
	private String valueSelect;
	/**
	 * 属性类型
	 */
	private Integer attrType;
	/**
	 * 启用
	 */
	private Long enable;
	/**
	 * 分类ID
	 */
	private Integer categoryId;
	/**
	 * 描述
	 */
	private Integer showDesc;




}
