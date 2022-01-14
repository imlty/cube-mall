package com.kkb.cubemall.order.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName OAuth2Properties
 * @Description
 * @Author hubin
 * @Date 2021/5/24 17:56
 * @Version V1.0
 **/
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "github")
public class OAuth2Properties {

    //客户端ID
    private String clientId;
    //客户端秘钥
    private String clientSecret;
    //用户授权地址（返回授权码）
    private String authorizationUrl;
    //回调地址，获取access_token
    private String redirectUrl;
    //认证服务器生成access_token
    private String accessTokenUrl;
    //获取用户身份信息
    private String userInfoUrl;

}

