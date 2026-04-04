package com.salman.ChatAppBackend.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.salman.ChatAppBackend.DTOs.MessageDTO;
import com.salman.ChatAppBackend.models.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "media", ignore = true)
    MessageDTO toDto(Message message); 

    List<MessageDTO> toDtoList(List<Message> messages); 

    Message toEntity(MessageDTO messageDTO); 
    
}
