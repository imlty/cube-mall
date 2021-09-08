package com.kkb.cubemall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sku??Ϣ
 * 
 * @author imlty
 * @email imlty626@qq.com
 * @date 2021-09-08 00:57:02
 */
@Data
@TableName("tb_sku_info")
public class SkuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId
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
