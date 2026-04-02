package com.example.demo.service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.common.Result;

public interface UserService {

    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);
}
