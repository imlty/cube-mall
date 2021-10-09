package com.kkb.cubemall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CubemallCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 可以携带 cookie 信息
        config.addAllowedOrigin("*"); // 允许所有请求源访问
        config.addAllowedHeader("*"); // 允许所有的请求头信息
        config.addAllowedMethod("*"); // 允许所有的请求方式 [get post delete potion]

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 代表对所有访问路径过滤

        return new CorsWebFilter(source);
    }

}
