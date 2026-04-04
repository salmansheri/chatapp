package com.salman.ChatAppBackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salman.ChatAppBackend.models.User;

public interface UserRepository extends JpaRepository<User, String>{

    // @Query(name = UserConstants.FIND_USER_BY_EMAIL)
    Optional<User> findByEmail(String userEmail);
    
}
