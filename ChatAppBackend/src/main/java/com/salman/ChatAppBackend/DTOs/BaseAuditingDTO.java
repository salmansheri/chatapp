package com.salman.ChatAppBackend.DTOs;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class BaseAuditingDTO {
    
    private LocalDateTime createdDate; 

   
    private LocalDateTime lastModifiedDate; 
    
}
