package com.example.demo.service;

import com.example.demo.DTO.ChatRequestDTO;
import com.example.demo.VO.ChatResponseVO;

public interface ChatService {
    String chat(String message);

    ChatResponseVO chat(ChatRequestDTO requestDTO);
}