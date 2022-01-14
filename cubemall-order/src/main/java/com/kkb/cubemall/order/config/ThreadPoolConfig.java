package com.kkb.cubemall.order.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPoolConfig
 * @Description
 * @Author hubin
 * @Date 2021/5/26 17:15
 * @Version V1.0
 **/
@EnableConfigurationProperties(ThreadPoolProperties.class)
@Configuration
public class ThreadPoolConfig {


    @Bean
    public ThreadPoolExecutor executor(ThreadPoolProperties poolProperties){
        return new ThreadPoolExecutor(
                poolProperties.getCoreSize(),
                poolProperties.getMaxSize(),
                poolProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

}

