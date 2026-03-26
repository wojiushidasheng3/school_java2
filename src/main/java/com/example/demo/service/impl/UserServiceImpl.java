package com.example.demo.service.impl;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.common.ResponseCode;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;

    private static final Map<String,String> userDb = new HashMap<>();

    @Override
    public Result<String> register(UserDTO userDTO) {
        if(userDb.containsKey(userDTO.getUsername())){
            return Result.error(ResponseCode.USER_HAS_EXISTED.getMessage());
        }

        userDb.put(userDTO.getUsername(),userDTO.getPassword());
        return Result.success("注册成功",ResponseCode.SUCCESS.getMessage());
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        if(!userDb.containsKey(userDTO.getUsername())){
            return Result.error(ResponseCode.USER_NOT_EXIST.getMessage());
        }

        String dbPassword = userDb.get(userDTO.getUsername());
        if(dbPassword.equals(userDTO.getPassword())){
            return Result.error(ResponseCode.PASSWORD_ERROR.getMessage());
        }
        return Result.success("登录成功");
    }
}