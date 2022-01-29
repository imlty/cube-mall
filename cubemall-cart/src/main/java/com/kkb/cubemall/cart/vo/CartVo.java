package com.kkb.cubemall.cart.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName CartVo
 * @Description
 * @Author hubin
 * @Date 2021/5/26 12:51
 * @Version V1.0
 **/
@ToString
@Data
public class CartVo {

    // 商品详细选项
    private List<CartItemVo> items;

    // 商品总数量
    private Integer countNum;

    // 商品总价
    private BigDecimal totalAccount;

    // 属性，减免价格
    private BigDecimal reducePrice = new BigDecimal("0");


    // 计算总价格
    public BigDecimal getTotalAccount(){

        // 定义变量实现总价的叠加
        BigDecimal account = new BigDecimal("0");

        // 循环获取商品详情，计算总价
        if(items!=null && items.size()>0){

            for (CartItemVo item : items) {
                // 判断商品是否被选中
                if(item.isCheck()){

                    // 获取单个商品总价
                    BigDecimal totalPrice = item.getTotalPrice();
                    account = account.add(totalPrice);
                }

            }
        }

        // 减免优惠价格
        BigDecimal subtract = account.subtract(getReducePrice());

        return subtract;

    }

    public Integer getCountNum(){
        Integer count = 0;
        if(items!=null && items.size()>0){
            for (CartItemVo item : items) {
                // 判断商品是否被选中
                if(item.isCheck()){

                    count += item.getCount();
                }

            }

        }

        return count;
    }





}

