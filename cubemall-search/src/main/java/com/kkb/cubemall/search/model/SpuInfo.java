package com.kkb.cubemall.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Document(indexName = "cube_goods")
@Data
public class SpuInfo implements Serializable {
    //商品id，同时也是商品编号
    @Id
    @Field(index = true, store = true, type = FieldType.Long)
    private Long id;

    //SPU名称
    @Field(index = true, store = true, type = FieldType.Text, analyzer = "ik_smart")
    private String spuName;

    //商品简介
    @Field(index = true, store = true, type = FieldType.Text, analyzer = "ik_smart")
    private String description;

    //商品价格，单位为：元
    @Field(index = true, store = true, type = FieldType.Double)
    private Double price;

    //商品图片
    @Field(index = false, store = true, type = FieldType.Text)
    private String image;

    //类目ID
    @Field(index = true, store = true, type = FieldType.Long)
    private Long categoryId;

    //类目名称
    @Field(index = true, store = true,type = FieldType.Keyword)
    private String categoryName;


    //品牌ID
    @Field(index = true, store = true, type = FieldType.Long)
        private Long brandId;

    //品牌名称
    @Field(index = true, store = true,type = FieldType.Keyword)
    private String brandName;

    //更新时间
    private Date updateTime;

}
