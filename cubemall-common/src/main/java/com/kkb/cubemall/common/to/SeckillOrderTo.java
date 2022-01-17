package com.kkb.cubemall.common.to;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**

 **/

@Data
public class SeckillOrderTo implements Serializable {

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 会员ID
     */
    private Long memberId;


}
