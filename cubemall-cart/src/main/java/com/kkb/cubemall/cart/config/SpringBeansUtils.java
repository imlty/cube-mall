package com.kkb.cubemall.cart.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpringBeansUtils
 * @Description
 * @Author hubin
 * @Date 2021/5/26 16:23
 * @Version V1.0
 **/
@Component
public class SpringBeansUtils implements ApplicationContextAware{

    // 定一个spring 上下文的变量
    private static ApplicationContext applicationContext;

    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeansUtils.applicationContext = applicationContext;
    }

    // 获取上下文对象
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 根据名称获取spring对象
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    // 根据class类获取对象
    public static Object getBeanClass(Class clazz){
        return getApplicationContext().getBean(clazz);
    }


}

