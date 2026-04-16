package com.example.demo.service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.VO.UserDetailVO;
import com.example.demo.common.Result;

public interface UserService {

    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);

    Result<Object> getUserPage(Integer pageNum,Integer pageSize);
    
    // 新增方法
    Result<UserDetailVO> getUserDetail(Long userId);
    Result<String> updateUserInfo(User userInfo);
    Result<String> deleteUser(Long userId);
}