package com.salman.ChatAppBackend.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.salman.ChatAppBackend.DTOs.ChatResponseDTO;
import com.salman.ChatAppBackend.models.Chat;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mapping(target = "name", expression = "java(chat.getChatName(senderId))")
    @Mapping(target = "unreadCount", expression = "java(chat.getUnreadmessages(senderId))")
    @Mapping(target = "lastMessage", expression = "java(chat.getLastMessage())")
    @Mapping(target = "lastMessageTime", expression = "java(chat.getLastMessageTime())")
    @Mapping(target = "senderId", source = "sender.userId")
    @Mapping(target = "receiverId", source = "recipient.userId")
    @Mapping(target = "isRecipientOnline", expression = "java(chat.getRecipient().isUserOnline())")
    ChatResponseDTO toDto(Chat chat, @org.mapstruct.Context String senderId);

     @Mapping(target = "name", expression = "java(chat.getChatName(senderId))")
    @Mapping(target = "unreadCount", expression = "java(chat.getUnreadmessages(senderId))")
    @Mapping(target = "lastMessage", expression = "java(chat.getLastMessage())")
    @Mapping(target = "lastMessageTime", expression = "java(chat.getLastMessageTime())")
    @Mapping(target = "senderId", source = "sender.userId")
    @Mapping(target = "receiverId", source = "recipient.userId")
    @Mapping(target = "isRecipientOnline", expression = "java(chat.getRecipient().isUserOnline())")
    List<ChatResponseDTO> toDtoList(List<Chat> chat, @org.mapstruct.Context String senderId);
   

    Chat toEntity(ChatResponseDTO chatResponseDTO); 
    

    
    
}
