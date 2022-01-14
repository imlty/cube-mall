package com.kkb.cubemall.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.omg.CORBA.ServerRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CookieConfig
 * @Description
 * @Author hubin
 * @Date 2021/6/1 10:51
 * @Version V1.0
 **/
@Configuration
public class CookieConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {

                //获取请求上下文对象
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                // 判断请求是否存在
                if(requestAttributes!=null){
                    HttpServletRequest request = requestAttributes.getRequest();
                    if(request != null){
                        // 把cookie信息实现共享
                        String cookie = request.getHeader("Cookie");
                        requestTemplate.header("Cookie",cookie);
                    }
                }

            }
        };
    }


}

