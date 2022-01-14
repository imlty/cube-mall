package com.kkb.cubemall.order.service.dto;

import com.kkb.cubemall.order.entity.OrderEntity;
import com.kkb.cubemall.order.entity.OrderItemEntity;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OrderDTO
 * @Description
 * @Author hubin
 * @Date 2021/6/4 15:33
 * @Version V1.0
 **/
@ToString
@Data
public class OrderDTO {

    private OrderEntity orderEntity;

    private List<OrderItemEntity> orderItemList;

    /*实际支付价格*/
    private BigDecimal payPrice;


}

