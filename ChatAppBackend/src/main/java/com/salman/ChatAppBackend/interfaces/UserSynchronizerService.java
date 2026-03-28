package com.salman.ChatAppBackend.interfaces;

import org.springframework.security.oauth2.jwt.Jwt;

public interface UserSynchronizerService {

    void synchronizeWithIdp(Jwt token);
    
}
