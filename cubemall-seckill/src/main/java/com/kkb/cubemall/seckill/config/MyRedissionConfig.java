package com.kkb.cubemall.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyRedissionConfig {

    /**
     * 当前的Redisson的配置
     * @return
     */
    @Bean(destroyMethod="shutdown")
    public RedissonClient redissonClient() {
        //1、创建配置，配置redis地址
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}
