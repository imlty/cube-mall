package com.kkb.cubemall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class MergeVo {

    private Long purchaseId; //采购单ID
    private List<Long> items; // 合并采购项ID的集合
}
