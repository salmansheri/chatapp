package com.salman.ChatAppBackend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salman.ChatAppBackend.DTOs.ChatResponseDTO;
import com.salman.ChatAppBackend.DTOs.CreateChatResponseDTO;
import com.salman.ChatAppBackend.interfaces.ChatService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Tag(name = "Chats")
public class ChatController {

    private final ChatService chatService; 

    @PostMapping()
    public ResponseEntity<CreateChatResponseDTO> createChat(
        @RequestParam(name = "sender-id") String senderId,
        @RequestParam(name = "receiver-id") String receiverId
    ) {
       final String chatId = chatService.createChat(senderId, receiverId); 

       CreateChatResponseDTO createChatResponseDTO = new CreateChatResponseDTO(); 

       createChatResponseDTO.setChatId(chatId);
       return new ResponseEntity<>(createChatResponseDTO, HttpStatus.OK); 
        
        
    }


    @GetMapping()
    public ResponseEntity<List<ChatResponseDTO>> getChats(Authentication authentication) {
        List<ChatResponseDTO> chatResponseDTOs = chatService.getChatsByRecieverId(authentication); 

        if (chatResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(chatResponseDTOs, HttpStatus.NO_CONTENT); 
        }

        return new ResponseEntity<List<ChatResponseDTO>>(chatResponseDTOs, HttpStatus.OK);
    }
    
    
    
}
