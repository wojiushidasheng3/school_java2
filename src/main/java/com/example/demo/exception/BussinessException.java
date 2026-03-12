package com.example.demo.exception;

import com.example.demo.common.ResponseCode;

public class BussinessException extends RuntimeException {
    /**
     * 业务异常
     * @param message 异常信息
     */
    private String message;
    /**
     * 异常码
     */
    private Integer code;

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(ResponseCode re){
        this.message = re.getMessage();
        this.code=re.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
