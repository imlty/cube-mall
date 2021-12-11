package com.kkb.cubemall.common.exception;

import lombok.Getter;

@Getter
public enum CubemallEnum {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数校验异常");

    private int code;
    private String msg;


    CubemallEnum(int code, String message) {
        this.code = code;
        this.msg = message;
    }

}
