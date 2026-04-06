package com.salman.ChatAppBackend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.salman.ChatAppBackend.DTOs.ApiResponseDTO;
import com.salman.ChatAppBackend.DTOs.MessageDTO;
import com.salman.ChatAppBackend.DTOs.MessageRequestDTO;
import com.salman.ChatAppBackend.interfaces.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<MessageDTO> saveMessage(@RequestBody MessageRequestDTO messageRequestDTO) {
        MessageDTO messageDTO = messageService.saveMessage(messageRequestDTO);

        return new ResponseEntity<MessageDTO>(messageDTO, HttpStatus.CREATED);

    }

    // @GetMapping()
    // public ResponseEntity<> getMethodName(@RequestParam String param) {
    // return new ResponseEntity<>();
    // }\

    @PostMapping(value = "/update-media", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponseDTO> saveMessage(@RequestParam("chat-id") String chatId,
            @RequestParam("file") MultipartFile file, Authentication authentication) {

        ApiResponseDTO apiResponseDTO = messageService.uploadMediaMessage(chatId, file, authentication);

        return new ResponseEntity<ApiResponseDTO>(apiResponseDTO, HttpStatus.CREATED);

    }

    @PatchMapping
    public ResponseEntity<ApiResponseDTO> setMessageToSeen(@RequestParam("chat-id") String chatId,
            Authentication authentication) {
        ApiResponseDTO response = messageService.setMessagesToSeen(chatId, authentication);

        return new ResponseEntity<ApiResponseDTO>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable String chatId) {

        return new ResponseEntity<List<MessageDTO>>(messageService.findChatMessages(chatId), HttpStatus.OK);
    }

}
