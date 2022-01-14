package com.kkb.cubemall.order.web.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品库存
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:09
 */
@Data
public class StockSkuVo implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * sku_id
	 */
	private Long skuId;

	private Boolean hasStock;

}
