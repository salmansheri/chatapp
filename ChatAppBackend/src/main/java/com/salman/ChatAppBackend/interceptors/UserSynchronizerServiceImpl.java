package com.salman.ChatAppBackend.interceptors;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.salman.ChatAppBackend.interfaces.UserSynchronizerService;
import com.salman.ChatAppBackend.mappers.UserMapper;
import com.salman.ChatAppBackend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizerServiceImpl implements UserSynchronizerService {

    private final UserRepository userRepository; 
    private final UserMapper userMapper; 

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with idp");
       
    }

    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims(); 

        if (attributes.containsKey("email")) {
            return Optional.of(attributes.get("email").toString()); 
        } 



        
        return Optional.empty(); 
    }
    
}
