package com.salman.ChatAppBackend.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.salman.ChatAppBackend.DTOs.UserDTO;
import com.salman.ChatAppBackend.interfaces.UserService;
import com.salman.ChatAppBackend.mappers.UserMapper;
import com.salman.ChatAppBackend.models.User;
import com.salman.ChatAppBackend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsersExceptSelf(Authentication authentication) {
        List<User> users = userRepository.findAllUsersExceptSelf(authentication.getName());

        List<UserDTO> userDtoList = userMapper.toDtoList(users);

        return userDtoList;

    }

}
