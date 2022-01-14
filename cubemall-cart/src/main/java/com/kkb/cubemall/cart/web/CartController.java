package com.kkb.cubemall.cart.web;

import com.kkb.cubemall.cart.service.CartService;
import com.kkb.cubemall.cart.vo.CartItemVo;
import com.kkb.cubemall.cart.vo.CartVo;
import com.kkb.cubemall.common.utils.R;
import jdk.nashorn.internal.ir.CatchNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @ClassName CartController
 * @Description
 * @Author hubin
 * @Date 2021/5/26 15:01
 * @Version V1.0
 **/
@Controller
public class CartController {

    // 注入购物车服务对象
    @Autowired
    private CartService cartService;


    /**
     * @Description: 添加购物车
     * @Author: hubin
     * @CreateDate: 2021/5/26 18:00
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 18:00
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/cart/addCart")
    public String addCart(@RequestParam("num") Integer num,
                          @RequestParam("skuId") Long skuId,
                          RedirectAttributes attributes){
        // 调用添加购物车方法，实现购物车添加
        CartItemVo itemVo = cartService.addToCart(skuId, num);

        // 属性将会自动追加重定向请求后面
        attributes.addAttribute("skuId",skuId);
        return "redirect:http://localhost:8083/cart/successPage";
    }


    /**
     * @Description: 调整到添加购物车成功的页面
     * @Author: hubin
     * @CreateDate: 2021/5/26 18:00
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 18:00
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @GetMapping("/cart/successPage")
    public String successPage(@RequestParam("skuId") Long skuId,
                              Model model){

        // 查询此次添加的购物车数据
        CartItemVo cartItem = cartService.getCartItemData(skuId);

        // 放入作用域
        model.addAttribute("item", cartItem);

        return "success-cart";

    }

    /**
     * @Description: 购物车结算页面： 购物车列表页面
     * @Author: hubin
     * @CreateDate: 2021/5/27 14:04
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 14:04
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("cart/list")
    public String getCartList(Model model){

        // 调用服务层方法
        CartVo cartVo = cartService.getCartList();
        model.addAttribute("cart",cartVo);
        return "cart";
    }


    /**
     * @Description: 购物车数量的改变
     * @Author: hubin
     * @CreateDate: 2021/5/27 17:40
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 17:40
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @GetMapping("cart/changeItem")
    public String changeItemNum(@RequestParam("skuId") Long skuId,
                                @RequestParam("num") Integer num){

        //调用服务端更新购物车数量方法
        cartService.changeItemNum(skuId,num);

        return "redirect:http://localhost:8083/cart/list";
    }


    /**
     * @Description: 购物车商品删除实现
     * @Author: hubin
     * @CreateDate: 2021/5/27 18:06
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 18:06
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/cart/delete")
    public String deleteCart(@RequestParam("skuId") Long skuId){

        // 调用服务层接口，实现购物车数据删除即可
        cartService.deleteCart(skuId);

        return "redirect:http://localhost:8083/cart/list";
    }

    /**
     * @Description: 订单结算获取购物清单
     * @Author: hubin
     * @CreateDate: 2021/5/31 15:45
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/31 15:45
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/cart/item/list")
    @ResponseBody
    public List<CartItemVo> getCartitemList(){
        List<CartItemVo> cartItemVoList = cartService.getCartitemList();
        return cartItemVoList;
    }


}

