package com.salman.ChatAppBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salman.ChatAppBackend.models.User;

public interface UserRepository extends JpaRepository<User, String>{
    
}
