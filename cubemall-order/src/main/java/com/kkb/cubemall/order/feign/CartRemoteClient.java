package com.kkb.cubemall.order.feign;

import com.kkb.cubemall.order.web.vo.CartItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName CartRemoteClient
 * @Description
 * @Author hubin
 * @Date 2021/5/31 15:40
 * @Version V1.0
 **/
@FeignClient(value = "cubemall-cart")
public interface CartRemoteClient {

    @RequestMapping("/cart/item/list")
    public List<CartItemVo> getCartItems();

}
