package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.common.ResponseCode;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        /*QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        User existingUser = userMapper.selectOne(queryWrapper);
        
        if (existingUser != null) {
            return Result.error(ResponseCode.USER_HAS_EXISTED.getMessage());
        }

        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        userMapper.insert(user);
        return Result.success("注册成功", ResponseCode.SUCCESS.getMessage());*/

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if(user != null){
            return Result.error(ResponseCode.USER_HAS_EXISTED.getMessage());
        }

        User us = new User();
        us.setUsername(userDTO.getUsername());
        us.setPassword(userDTO.getPassword());
        userMapper.insert(us);
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        
        if (user == null) {
            return Result.error(ResponseCode.USER_NOT_EXIST.getMessage());
        }

        if (!user.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResponseCode.PASSWORD_ERROR.getMessage());
        }
        
        String token = "Bearer " + user.getUsername() + "." + System.currentTimeMillis();
        return Result.success("登录成功", token);
    }

    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if(user == null){
            return Result.error(ResponseCode.USER_NOT_EXIST.getMessage());
        }
        return Result.success(user.getUsername());
    }
}