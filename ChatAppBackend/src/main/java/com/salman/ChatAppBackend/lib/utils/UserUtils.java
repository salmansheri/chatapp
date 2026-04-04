package com.salman.ChatAppBackend.lib.utils;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.salman.ChatAppBackend.models.User;


public  class UserUtils {
   
    public static  User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User(); 

        if (attributes.containsKey("sub")) {
            user.setUserId(attributes.get("sub").toString());
        }

        if (attributes.containsKey("given_name"))  {
            user.setFirstName(attributes.get("given_name").toString());
        } else if (attributes.containsKey("nickname")) {
            user.setFirstName(attributes.get("nickname").toString());

        }

         if (attributes.containsKey("family_name")) {
            user.setLastName(attributes.get("family_name").toString());
        }

         if (attributes.containsKey("email")) {
            user.setEmail(attributes.get("email").toString());
        }

        user.setLastSeen(LocalDateTime.now());

        return user; 

        



    }
    
}
