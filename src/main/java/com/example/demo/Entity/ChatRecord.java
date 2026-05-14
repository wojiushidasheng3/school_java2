package com.example.demo.Entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class ChatRecord {

    private String sessionId;
    private String userMessage;
    private String assistantMessage;
    private LocalDateTime createTime;
}
