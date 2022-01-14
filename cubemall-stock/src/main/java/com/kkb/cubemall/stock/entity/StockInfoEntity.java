package com.kkb.cubemall.stock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 仓库信息
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:10
 */
@Data
@TableName("tb_stock_info")
public class StockInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 仓库名
	 */
	private String name;
	/**
	 * 仓库地址
	 */
	private String address;
	/**
	 * 区域编码
	 */
	private String areacode;

}
