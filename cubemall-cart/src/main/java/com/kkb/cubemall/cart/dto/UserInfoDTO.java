package com.kkb.cubemall.cart.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName UserInfoDTO
 * @Description
 * @Author hubin
 * @Date 2021/5/26 14:30
 * @Version V1.0
 **/
@ToString
@Data
public class UserInfoDTO {

    private Long userId; // 用户登录使用的
    private String userKey; // 标识临时用户的key,未登录使用这个值

    private Boolean tempUser = false;


}

