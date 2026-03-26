package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public Result() {
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> Result<T> success(){
        return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(String msg){
        return new Result<>(ResponseCode.SUCCESS.getCode(), msg, null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }
    public static <T> Result<T> success(String msg,T data){
        return new Result<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> error(Integer code, String message){
        return new Result<>(code, message, null);
    }
    public static <T> Result<T> error(String message){
        return new Result<>(ResponseCode.ERROR.getCode(), message, null);
    }

}