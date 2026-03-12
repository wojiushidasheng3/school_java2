package com.example.demo.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(BussinessException.class)
    public String handleBussinessException(BussinessException e){
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
        return Result.error(500,"系统内部错误：" + e.getMessage());
    }

}