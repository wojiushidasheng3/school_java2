package com.example.demo.common;

public enum ResponseCode {

    SUCCESS(200,"操作成功"),
    ERROR(500,"操作失败"),
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
