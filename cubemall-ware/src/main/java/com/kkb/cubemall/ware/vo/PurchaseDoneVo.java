package com.kkb.cubemall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 完成采购
 */
@Data
public class PurchaseDoneVo {

    /**
     * 采购单id
     */
    @NotNull
    private Long id;

    private List<PurchaseItemDoneVo> items;
}
