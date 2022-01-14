package com.kkb.cubemall.order.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName UserDTO
 * @Description
 * @Author hubin
 * @Date 2021/5/18 16:45
 * @Version V1.0
 **/
@ToString
@Data
public class UserDTO {

    private String username;
    private String password;

    // 是否自动登录
    private String auto;

}

