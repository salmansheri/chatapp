package com.salman.ChatAppBackend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salman.ChatAppBackend.DTOs.UserDTO;
import com.salman.ChatAppBackend.interfaces.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; 

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(Authentication authentication) {
        List<UserDTO> userDTOs = userService.getAllUsersExceptSelf(authentication); 
        return new ResponseEntity<List<UserDTO>>(userDTOs, HttpStatus.OK);
    }
    

    
}
