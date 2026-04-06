package com.salman.ChatAppBackend.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.salman.ChatAppBackend.DTOs.ApiResponseDTO;
import com.salman.ChatAppBackend.DTOs.MessageDTO;
import com.salman.ChatAppBackend.DTOs.MessageRequestDTO;

public interface MessageService {

    MessageDTO saveMessage(MessageRequestDTO MessageRequestDTO); 
    List<MessageDTO> findChatMessages(String chatId);
     public ApiResponseDTO setMessagesToSeen(String chatId, Authentication authentication);
     public ApiResponseDTO uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication); 
    
}
