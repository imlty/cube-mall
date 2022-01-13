package com.kkb.cubemall.ware.vo;

import lombok.Data;

/**
 * 完成采购(采购项)
 */
@Data
public class PurchaseItemDoneVo {

    /**
     * 采购项id
     */
    private Long itemId;

    /**
     * 采购项的状态
     */
    private Integer status;

    /**
     * 原因
     */
    private String reason;
}
