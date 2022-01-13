package com.kkb.cubemall.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients
public class  CubemallWareApplication {

	public static void main(String[] args) {
		SpringApplication.run(CubemallWareApplication.class, args);
	}

}
