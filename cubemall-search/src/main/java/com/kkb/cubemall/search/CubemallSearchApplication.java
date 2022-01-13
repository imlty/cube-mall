package com.kkb.cubemall.search;

import com.kkb.cubemall.search.model.SpuInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: sublun
 * @Date: 2021/4/25 18:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.kkb.cubemall.search.dao")
public class CubemallSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(CubemallSearchApplication.class);
    }
}
