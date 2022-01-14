package com.kkb.cubemall.cart.service;

import com.kkb.cubemall.cart.vo.CartItemVo;
import com.kkb.cubemall.cart.vo.CartVo;

import java.util.List;

/**
 * @ClassName CartService
 * @Description 购物车实现服务端接口
 * @Author hubin
 * @Date 2021/5/26 16:55
 * @Version V1.0
 **/
public interface CartService {

    /**
     * @Description: 购物车添加的接口
     * @Author: hubin
     * @CreateDate: 2021/5/26 16:56
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 16:56
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    public CartItemVo addToCart(Long skuId,Integer num);

    // 查询此次添加的购物车数据
    CartItemVo getCartItemData(Long skuId);

    /**
     * @Description: 购物车结算页面： 购物车列表页面
     * @Author: hubin
     * @CreateDate: 2021/5/27 14:04
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 14:04
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    public CartVo getCartList();

    /**
     * @Description: 购物车数量的改变
     * @Author: hubin
     * @CreateDate: 2021/5/27 17:40
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 17:40
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    public void changeItemNum(Long skuId,Integer num);

    /**
     * @Description: 购物车商品删除实现
     * @Author: hubin
     * @CreateDate: 2021/5/27 18:06
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 18:06
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    public void deleteCart(Long skuId);

    /**
     * @Description: 订单结算获取购物清单
     * @Author: hubin
     * @CreateDate: 2021/5/31 15:45
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/31 15:45
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */

    List<CartItemVo> getCartitemList();
}
