package com.kkb.cubemall.order.web.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员收货地址
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:47
 */
@Data
public class UserReceiveAddressVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * user_id
	 */
	private Long userId;
	/**
	 * 收货人姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 邮政编码
	 */
	private String postCode;
	/**
	 * 省份/直辖市
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 区
	 */
	private String region;
	/**
	 * 详细地址(街道)
	 */
	private String detailAddress;
	/**
	 * 省市区代码
	 */
	private String areacode;
	/**
	 * 是否默认
	 */
	private Integer defaultStatus;

}
