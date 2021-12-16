package com.kkb.cubemall.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author 96217
 */
@Data
@Document(indexName = "goods_1", shards = 5, replicas = 1)
public class SpuInfo {
    @Id
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_max_word")
    private String spuName;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_max_word")
    private String spuDescription;
    @Field(type = FieldType.Long)
    private Long categoryId;
    @Field(type = FieldType.Keyword, store = true)
    private String categoryName;
    @Field(type = FieldType.Long)
    private Long brandId;
    @Field(type = FieldType.Keyword, store = true)
    private String brandName;
    @Field(type = FieldType.Keyword, store = true, index = false)
    private String brandImage;
    @Field(type = FieldType.Date, store = true, format = DateFormat.basic_date_time)
    private Date updateTime;
    @Field(type = FieldType.Keyword, store = true, index = false)
    private String imgUrl;
    @Field(type = FieldType.Double, store = true)
    private Double price;
}
