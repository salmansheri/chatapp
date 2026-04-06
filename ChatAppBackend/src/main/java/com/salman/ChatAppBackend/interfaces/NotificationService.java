package com.salman.ChatAppBackend.interfaces;

import javax.management.Notification;

import com.salman.ChatAppBackend.DTOs.ApiResponseDTO;
import com.salman.ChatAppBackend.DTOs.NotificationDTO;

public interface NotificationService {
    ApiResponseDTO sendNotification(String userId, NotificationDTO notification); 
}
