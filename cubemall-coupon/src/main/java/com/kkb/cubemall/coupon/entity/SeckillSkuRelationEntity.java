package com.kkb.cubemall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 场次商品关联表
 * 
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-05-31 11:02:46
 */
@Data
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 场次ID
	 */
	private Long promotionSessionId;
	/**
	 * 商品ID
	 */
	private Long skuId;
	/**
	 * 商品价格
	 */
	private BigDecimal seckillPrice;
	/**
	 * 秒杀商品库存
	 */
	private BigDecimal seckillCount;
	/**
	 * 每人限购数量
	 */
	private BigDecimal seckillLimit;
	/**
	 * 排序
	 */
	private Integer seckillSort;

}
