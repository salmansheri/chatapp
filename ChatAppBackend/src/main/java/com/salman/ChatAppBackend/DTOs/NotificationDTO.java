package com.salman.ChatAppBackend.DTOs;

import com.salman.ChatAppBackend.enums.MessageType;
import com.salman.ChatAppBackend.enums.NotificationType;

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
public class NotificationDTO {
    private String chatId; 
    private String content; 
    private String senderId; 
    private String receiverId; 
    private String chatName; 
    private MessageType messageType; 
    private NotificationType notificationType; 
    private byte[] media; 

    
}
