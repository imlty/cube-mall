package com.kkb.cubemall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kkb.cubemall.common.valid.AddGroup;
import com.kkb.cubemall.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

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
	@NotNull(message = "修改必须指定品牌id",groups = {UpdateGroup.class})
	@Null(message = "新增不能指定id",groups = {AddGroup.class})
	private Integer id;
	/**
	 * 品牌名称
	 */
	@NotBlank(message = "品牌名必须提交",groups = {UpdateGroup.class,AddGroup.class})
	private String name;
	/**
	 * 品牌图片地址
	 */
	@NotBlank(message = "图片不能为空",groups = {AddGroup.class})
	@URL(message = "logo必须是一个合法的url地址",groups = {UpdateGroup.class,AddGroup.class})
	private String image;
	/**
	 * 品牌的首字母
	 */
	@NotEmpty(message = "品牌首字母不能为空串",groups = {UpdateGroup.class,AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索的首字母必须是一个字母")
	private String letter;
	/**
	 * 排序
	 */
	@NotNull(message ="排序不能为空",groups = {AddGroup.class})
	@Min(value = 0,message = "排序必须大于等于0",groups = {UpdateGroup.class,AddGroup.class})
	private Integer seq;

}
