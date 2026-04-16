package com.example.demo.common;

public enum ResponseCode {

    SUCCESS(200,"操作成功"),
    ERROR(500,"操作失败"),
    USER_HAS_EXISTED(4001,"该用户名已被注册"),
    USER_NOT_EXIST(4002,"该用户名不存在"),
    PASSWORD_ERROR(4003,"账号或密码错误"),
    PARAM_ERROR(4004,"参数错误"),
    ;

    /**
     * 响应状态码**/
    private final Integer code;


    /**
     * 响应提示信息**/
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }



    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}