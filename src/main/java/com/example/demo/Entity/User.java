package com.example.demo.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {
    @TableId
    private Long id;
    private String username;
    private String password;
    private String name;
    private Integer age;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}