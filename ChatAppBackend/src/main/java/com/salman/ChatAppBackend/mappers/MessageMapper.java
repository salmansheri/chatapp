package com.salman.ChatAppBackend.mappers;

import com.salman.ChatAppBackend.DTOs.MessageDTO;
import com.salman.ChatAppBackend.lib.utils.FileUtils;
import com.salman.ChatAppBackend.models.Message;


public class MessageMapper {

  public static MessageDTO toMessageResponse(Message message) {
    return MessageDTO.builder()
            .messageId(message.getMessageId())
            .content(message.getContent())
            .senderId(message.getSenderId())
            .receiverId(message.getReceiverId())
            .messageType(message.getMessageType())
            .messageState(message.getMessageState())
            .createdAt(message.getCreatedDate())
            .media(FileUtils.readFileFromLocation(message.getMediaFilePath()))
            .build(); 
  }
    
}
