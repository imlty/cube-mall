package com.kkb.cubemall.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: sublun
 * @Date: 2021/4/22 18:24
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.kkb.cubemall.search.dao")
public class CubemallSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(CubemallSearchApplication.class, args);
    }
}
