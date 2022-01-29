package com.kkb.cubemall.cart.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName CartItemVo
 * @Description
 * @Author hubin
 * @Date 2021/5/26 12:54
 * @Version V1.0
 **/
@ToString
@Data
public class CartItemVo {

    // sku商品id
    private Long skuId;

    //商品标题
    private String title;

    //商品图片
    private String image;

    //商品属性
    private List<String> skuAttr;

    //商品价格
    private BigDecimal price;

    //商品数量
    private Integer count;

    //总价格
    private BigDecimal totalPrice;

    // 是否选中
    private boolean check = true;

    // 计算单价*数量的商品总价
    public BigDecimal getTotalPrice(){
        return this.price.multiply(new BigDecimal(count+""));
    }

}

