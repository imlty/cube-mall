package com.kkb.cubemall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 品牌表
 * 
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
@Data
@TableName("tb_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	private Integer id;
	/**
	 * 品牌名称
	 */
	private String name;
	/**
	 * 品牌图片地址
	 */
	private String image;
	/**
	 * 品牌的首字母
	 */
	private String letter;
	/**
	 * 排序
	 */
	private Integer seq;

}
