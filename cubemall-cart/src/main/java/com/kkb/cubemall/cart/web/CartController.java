package com.kkb.cubemall.cart.web;

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
    public String addCart(Integer num,Long skuId){
        return "cart";
    }


}

