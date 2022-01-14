package com.kkb.cubemall.order.service.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName SubmitOrderDTO
 * @Description
 * @Author hubin
 * @Date 2021/6/4 15:30
 * @Version V1.0
 **/
@ToString
@Data
public class SubmitOrderDTO {

    /*收货地址*/
    private Long addrId;

    /*支付方式*/
    private Integer payType;

    /*防重令牌*/
    private String orderToken;


}

