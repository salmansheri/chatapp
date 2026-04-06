package com.salman.ChatAppBackend.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.salman.ChatAppBackend.DTOs.UserDTO;
import com.salman.ChatAppBackend.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  
   
    @Mapping(target = "online", expression = "java(user.isUserOnline())")
    UserDTO toDto(User user);

    List<UserDTO> toDtoList(List<User> users); 

   

    // User toEntity(UserDTO userDTO); 
    

    
    
}
