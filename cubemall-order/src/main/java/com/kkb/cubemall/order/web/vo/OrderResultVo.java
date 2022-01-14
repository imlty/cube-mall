package com.kkb.cubemall.order.web.vo;

import com.kkb.cubemall.order.entity.OrderEntity;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName OrderResultVo
 * @Description
 * @Author hubin
 * @Date 2021/6/4 15:32
 * @Version V1.0
 **/
@ToString
@Data
public class OrderResultVo {

    /*订单*/
    private OrderEntity order;

    /*订单异常状态*/
    private Integer code;


}

