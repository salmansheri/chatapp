package com.salman.ChatAppBackend.DTOs;

import com.salman.ChatAppBackend.enums.MessageState;
import com.salman.ChatAppBackend.enums.MessageType;
import com.salman.ChatAppBackend.models.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO extends BaseAuditingDTO {
    private Long messageId; 
    private String content; 
    private MessageState messageState; 
    private MessageType messageType; 
    private Chat chat; 
    private String senderId; 
    private String receiverId; 
    private byte[] media; 

    
}
