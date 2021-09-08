package com.kkb.cubemall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author imlty
 * @email imlty626@qq.com
 * @date 2021-09-08 00:57:02
 */
@Data
@TableName("tree")
public class TreeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer nodeId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer lft;
	/**
	 * 
	 */
	private Integer rgt;

}
