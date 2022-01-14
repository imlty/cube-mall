package com.kkb.cubemall.order.web;

import com.kkb.cubemall.order.service.OrderService;
import com.kkb.cubemall.order.web.vo.OrderConfirmVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName OrderWebController
 * @Description
 * @Author hubin
 * @Date 2021/5/30 15:32
 * @Version V1.0
 **/
@Controller
@Slf4j
public class OrderWebController {


    // 注入订单服务对象
    @Autowired
    private OrderService orderService;

    /**
     * @Description: 去掉订单结算页
     * @Author: hubin
     * @CreateDate: 2021/5/30 15:32
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/30 15:32
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/order/confirm")
    public String confirmOrder(Model model){

        // 查询计算页相关数据
        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("confirmVo",confirmVo);


        return "order";
    }

}

