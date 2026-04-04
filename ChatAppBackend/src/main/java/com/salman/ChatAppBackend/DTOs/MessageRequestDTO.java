package com.salman.ChatAppBackend.DTOs;

import com.salman.ChatAppBackend.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequestDTO {
    private String content; 
    private String senderId; 
    private String receiverId; 
    private MessageType messageType; 
    private String chatId; 
    
}
