package com.salman.ChatAppBackend.services;



import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.salman.ChatAppBackend.DTOs.ApiResponseDTO;
import com.salman.ChatAppBackend.DTOs.NotificationDTO;
import com.salman.ChatAppBackend.interfaces.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate; 

    @Override
    public ApiResponseDTO sendNotification(String userId, NotificationDTO notification) {
        log.info("Sending notification to {} with payload {}", userId, notification); 

        messagingTemplate.convertAndSendToUser(userId, "/chat", notification); 

        return new ApiResponseDTO("Notification Sent successfully for the User: " + userId + " with Payload: " + notification, true);
       
    }
    
}
