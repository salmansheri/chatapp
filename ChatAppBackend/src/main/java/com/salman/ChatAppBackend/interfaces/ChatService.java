package com.salman.ChatAppBackend.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.salman.ChatAppBackend.DTOs.ChatResponseDTO;

public interface ChatService {
    List<ChatResponseDTO> getChatsByRecieverId(Authentication currentUser); 
    String createChat(String senderId, String receiverId); 

    

    
}
