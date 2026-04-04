package com.salman.ChatAppBackend.DTOs;

import java.time.LocalDateTime;

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
public class ChatResponseDTO {
    private String chatId; 
    private String name; 
    private long unreadCount; 
    private String lastMessage;
    private LocalDateTime lastMessageTime; 
    private Boolean isRecipientOnline; 
    private String senderId; 
    private String receiverId;  
    private LocalDateTime createdDate; 
     private LocalDateTime lastModifiedDate; 

    
}
