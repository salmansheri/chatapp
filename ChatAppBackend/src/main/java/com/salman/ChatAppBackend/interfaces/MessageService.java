package com.salman.ChatAppBackend.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.salman.ChatAppBackend.DTOs.MessageDTO;
import com.salman.ChatAppBackend.DTOs.MessageRequestDTO;

public interface MessageService {

    void saveMessage(MessageRequestDTO MessageRequestDTO); 
    List<MessageDTO> findChatMessages(String chatId);
     public void setMessagesToSeen(String chatId, Authentication authentication); 
    
}
