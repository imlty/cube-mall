package com.kkb.cubemall.order.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName InterceptorConfig
 * @Description
 * @Author hubin
 * @Date 2021/5/26 15:16
 * @Version V1.0
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

    // 实现拦截器注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OrderInterceptor())
                .addPathPatterns("/**");
    }
}

