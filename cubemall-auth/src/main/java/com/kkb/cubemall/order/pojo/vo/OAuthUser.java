package com.kkb.cubemall.order.pojo.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName OAuthUser
 * @Description
 * @Author hubin
 * @Date 2021/5/24 19:01
 * @Version V1.0
 **/
@Data
@ToString
public class OAuthUser {

    private String id;
    private String login;
    private String name;
    private String email;


}

