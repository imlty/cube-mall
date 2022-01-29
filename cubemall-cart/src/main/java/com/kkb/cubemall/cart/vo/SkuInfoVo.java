package com.kkb.cubemall.cart.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * sku
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-13 20:26:25
 */
@Data
public class SkuInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * spuId
	 */
	private Long spuId;
	/**
	 * sku名称
	 */
	private String skuName;
	/**
	 * sku描述
	 */
	private String skuDesc;
	/**
	 * 分类id
	 */
	private Long categoryId;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 默认sku图片
	 */
	private String skuDefaultImg;
	/**
	 * sku名称
	 */
	private String skuTitle;
	/**
	 * sku子名称
	 */
	private String skuSubtitle;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 销售数量
	 */
	private Long saleCount;

}
