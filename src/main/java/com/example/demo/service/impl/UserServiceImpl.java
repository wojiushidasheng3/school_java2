package com.example.demo.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.VO.UserDetailVO;
import com.example.demo.common.ResponseCode;
import com.example.demo.common.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private UserInfoMapper userInfoMapper;

    // 缓存键前缀
    private static final String CACHE_KEY_PREFIX = "user:detail:";

    @Override
    public Result<String> register(UserDTO userDTO) {
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

    @Override
    public Result<Object> getUserPage(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);

        Page<User> userPage = userMapper.selectPage(page, null);
        return Result.success(userPage);

    }

    @Override
    public Result<UserDetailVO> getUserDetail(Long userId) {
        String key = CACHE_KEY_PREFIX + userId;

        // 1. 先查缓存
        try {
            String json = stringRedisTemplate.opsForValue().get(key);
            if (json != null && !json.isBlank()) {
                try {
                    UserDetailVO cacheVO = JSONUtil.toBean(json, UserDetailVO.class);
                    return Result.success(cacheVO);
                } catch (Exception e) {
                    log.error("缓存数据解析异常，删除缓存: {}", key, e);
                    stringRedisTemplate.delete(key);
                }
            }
        } catch (Exception e) {
            log.warn("Redis 查询失败，跳过缓存: {}", e.getMessage());
        }

        // 2. 查数据库
        UserDetailVO detail = userInfoMapper.getUserDetail(userId);
        if (detail == null) {
            return Result.error(ResponseCode.USER_NOT_EXIST.getMessage());
        }

        // 3. 写缓存
        try {
            String jsonStr = JSONUtil.toJsonStr(detail);
            log.info("写入缓存 - key: {}, value: {}", key, jsonStr);
            stringRedisTemplate.opsForValue().set(
                key,
                jsonStr,
                10,
                TimeUnit.MINUTES
            );
            log.info("缓存写入成功");
        } catch (Exception e) {
            log.error("Redis 写入失败: {}", e.getMessage(), e);
        }

        return Result.success(detail);
    }

    @Override
    @Transactional
    public Result<String> updateUserInfo(User userInfo) {
        // 参数校验,userInfo 不能为空，并且 userId 不能为空,后面删除 Redis 缓存时
        if (userInfo == null || userInfo.getId() == null) {
            return Result.error(ResponseCode.PARAM_ERROR.getMessage());
        }
        
        // 更新用户信息
        int rows = userMapper.updateById(userInfo);
        if (rows == 0) {
            return Result.error(ResponseCode.USER_NOT_EXIST.getMessage());
        }
        
        // 删除缓存
        String key = CACHE_KEY_PREFIX + userInfo.getId();
        stringRedisTemplate.delete(key);
        
        return Result.success("更新成功");
    }

    @Override
    @Transactional
    public Result<String> deleteUser(Long userId) {
        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error(ResponseCode.USER_NOT_EXIST.getMessage());
        }
        
        // 删除用户
        userMapper.deleteById(userId);
        
        // 删除缓存
        String key = CACHE_KEY_PREFIX + userId;
        stringRedisTemplate.delete(key);
        
        return Result.success("删除成功");
    }
}