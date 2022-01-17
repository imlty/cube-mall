package com.kkb.cubemall.search.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kkb.cubemall.common.valid.AddGroup;
import com.kkb.cubemall.common.valid.ListValue;
import com.kkb.cubemall.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-07 11:38:21
 */
@Data
@TableName("tb_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 * 新增，ID 自动生成 不能指定
	 * 修改，ID 必须要指定
	 */
	@NotNull(message = "修改品牌必须指定ID",groups = {UpdateGroup.class})
	@Null(message = "新增品牌不能指定ID",groups = {AddGroup.class})
	@TableId
	private Integer id;
	/**
	 * 品牌名称
	 */
	@NotBlank(message="品牌不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌图片地址
	 */
	@NotEmpty(groups = {AddGroup.class})
	@URL(message = "图片必须是一个合法url地址",groups = {AddGroup.class,UpdateGroup.class})
	private String image;
	/**
	 * 品牌的首字母
	 */
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z]$",message = "品牌首字母必须是字母")
	private String letter;
	/**
	 * 排序
	 */
	@NotNull
//	@Min(value = 0,message = "排序不能小于0")
	@ListValue(vals = {0,1,2,3},groups = {AddGroup.class})
	private Integer seq;

}
