package com.example.demo.common;

import java.util.List;


public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg, List<String> data) {
        this.code = code;
        this.msg = msg;
        this.data = (T) data;
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

    public List<String> getData() {
        return (List<String>) data;
    }

    public void setData(List<String> data) {
        this.data = (T) data;
    }


    public static <T> String success(){
        return "操作成功";
    }

    public static <T> String error(Integer code, String message){
        return "操作失败";
    }
}
