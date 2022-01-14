package com.kkb.cubemall.order.utils;

/**
 * @ClassName Constants
 * @Description
 * @Author hubin
 * @Date 2021/5/18 16:57
 * @Version V1.0
 **/
public class Constants {
    // 用户身份信息存储的key:redis存储的
    public static final String REDIS_LOGIN_KEY="CUBE_LOGIN_TOKEN:";

    // 存储cookie中token的key
    public static final String COOKIE_LOGIN_KEY="COOKIE_LOGIN_KEY";

    // 存储临时用户cookie key
    public static final String COOKIE_TEMP_CART_KEY="TEMP_USER_KEY";

    // 存储临时用户cookie key 超时时间
    public static final int COOKIE_TEMP_CART_KEY_TIMEOUT= 24*60*60;

    // 购物车key
    public static final String CART_PREFIX="CART_KEY:";

    // 订单提交的防重令牌
    public static final String ORDER_TOKEN_PREFIX = "order_repeat_token:";



}

