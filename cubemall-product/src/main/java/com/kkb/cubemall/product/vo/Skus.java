/**
  * Copyright 2021 json.cn 
  */
package com.kkb.cubemall.product.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Skus {

    private List<Attr> attr;
    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private String skuSubtitle;
    private List<Images> images;
    private List<String> descar;
    private int fullCount;
    private int countStatus;
    private BigDecimal fullPrice;
    private int priceStatus;

}