package com.example.demo.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_info")
public class UserInfo {
    @TableId
    private Long id;
    private Long userId;
    private String realName;
    private String phone;
    private String address;
    
    public UserInfo() {
    }
}