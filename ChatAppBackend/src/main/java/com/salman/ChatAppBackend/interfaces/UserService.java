package com.salman.ChatAppBackend.interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.salman.ChatAppBackend.DTOs.UserDTO;

public interface UserService {

    List<UserDTO> getAllUsersExceptSelf(Authentication authentication); 

    
} 
