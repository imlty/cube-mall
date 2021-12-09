/**
  * Copyright 2021 json.cn 
  */
package com.kkb.cubemall.product.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SpuSaveVo {

    private String spuName;
    private String spuDescription;
    private Long categoryId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    private String decript;
    private List<String> images;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;

}