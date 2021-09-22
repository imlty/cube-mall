package com.kkb.cubemall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spu信息
 * 
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-19 18:24:09
 */
@Data
@TableName("tb_spu_info")
public class SpuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增ID
	 */
	@TableId
	private Long id;
	/**
	 * spu名称
	 */
	private String spuName;
	/**
	 * spu描述
	 */
	private String spuDescription;
	/**
	 * 分类ID
	 */
	private Long categoryId;
	/**
	 * 品牌ID
	 */
	private Long brandId;
	/**
	 * 权重
	 */
	private BigDecimal weight;
	/**
	 * 发布状态
	 */
	private Integer publishStatus;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

}
