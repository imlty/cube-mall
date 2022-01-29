/**
  * Copyright 2019 bejson.com
  */
package com.kkb.cubemall.product.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
http://
 *  *
 *  * @author bejson.com (i@bejson.com)
 *  * @website https://www.bejson.com/json2javapojo/new/
 */
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
