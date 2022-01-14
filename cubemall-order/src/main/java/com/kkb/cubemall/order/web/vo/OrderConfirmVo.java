package com.kkb.cubemall.order.web.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderConfirmVo
 * @Description
 * @Author hubin
 * @Date 2021/5/31 14:49
 * @Version V1.0
 **/
@ToString
@Data
public class OrderConfirmVo {

    /*用户收货地址列表信息*/
    private List<UserReceiveAddressVo> userReceiveAddressList;

    /*用户购物车清单*/
    private List<CartItemVo> items;

    /*优惠券*/
    private Integer integration;

    /*是否有库存*/
    Map<Long,Boolean> stocks;

    // 防重令牌属性
    private String orderToken;

    /*购物车清单中商品总数*/
    private Integer countNum;
    /*购物清单总价格*/
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
                    // 获取单个商品总价
                    BigDecimal totalPrice = item.getTotalPrice();
                    account = account.add(totalPrice);
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

                    count += item.getCount();


            }

        }

        return count;
    }



}

