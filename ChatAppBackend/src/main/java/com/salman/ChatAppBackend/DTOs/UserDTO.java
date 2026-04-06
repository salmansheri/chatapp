package com.salman.ChatAppBackend.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.salman.ChatAppBackend.models.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseAuditingDTO {
    //  private static final int LAST_ACTIVE_INTERVAL = 5; 
    
    private String userId; 
    private String firstName; 
    private String lastName; 
    private String email; 
    private LocalDateTime lastSeen; 

    
  

    private boolean online; 
    
}
