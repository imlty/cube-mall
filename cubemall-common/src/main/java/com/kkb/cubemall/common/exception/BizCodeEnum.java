package com.kkb.cubemall.common.exception;

/**
 * 错误代码以及错误信息，字典表
 */
public enum BizCodeEnum {
    VAILD_EXCEPTION(10001,"参数校验失败"),
    TOO_MANY_REQUEST(10002,"请求访问太多"),
    UNKNONW_EXCEPTION(20000,"系统未知异常");

    private int code;
    private String message;

    BizCodeEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
