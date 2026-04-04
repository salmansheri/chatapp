package com.salman.ChatAppBackend.services;

import com.salman.ChatAppBackend.repositories.ChatRepository;
import com.salman.ChatAppBackend.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salman.ChatAppBackend.DTOs.ChatResponseDTO;
import com.salman.ChatAppBackend.interfaces.ChatService;
import com.salman.ChatAppBackend.mappers.ChatMapper;
import com.salman.ChatAppBackend.models.Chat;
import com.salman.ChatAppBackend.models.User;

// import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ChatResponseDTO> getChatsByRecieverId(Authentication currentUser) {
        final String userId = currentUser.getName();
        List<Chat> chats = chatRepository.findChatsBySenderId(userId);

        

        return chatMapper.toDtoList(chats, userId);

    }

    @Override
    public String createChat(String senderId, String receiverId) {
        Optional<Chat> existingChat = chatRepository.findChatBySenderIdAndReceiverId(senderId, receiverId);

        if (existingChat.isPresent()) {
            return existingChat.get().getChatId();
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id: " + senderId + " not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + receiverId + " not found"));

        Chat chat = new Chat();

        chat.setSender(sender);
        chat.setRecipient(receiver);
        Chat savedChat = chatRepository.save(chat);

        return savedChat.getChatId();

    }

}
