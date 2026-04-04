package com.salman.ChatAppBackend.services; 

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salman.ChatAppBackend.DTOs.MessageDTO;
import com.salman.ChatAppBackend.DTOs.MessageRequestDTO;
import com.salman.ChatAppBackend.enums.MessageState;
import com.salman.ChatAppBackend.interfaces.MessageService;
import com.salman.ChatAppBackend.mappers.MessageMapper;
import com.salman.ChatAppBackend.models.Chat;
import com.salman.ChatAppBackend.models.Message;
import com.salman.ChatAppBackend.repositories.ChatRepository;
import com.salman.ChatAppBackend.repositories.MessageRepository;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRespository;     
    private final ChatRepository chatRepository; 
    private final MessageMapper messageMapper; 


    @Override
    public void saveMessage(MessageRequestDTO messageRequestDTO) {
        log.info("Saving message");
       Chat chat = chatRepository.findById(messageRequestDTO.getChatId()).orElseThrow(() -> new EntityNotFoundException("Chat not found")); 

       Message message = new Message(); 

       message.setContent(messageRequestDTO.getContent());
       message.setChat(chat);
       message.setSenderId(messageRequestDTO.getSenderId());  
       message.setReceiverId(messageRequestDTO.getReceiverId());  
       message.setMessageType(messageRequestDTO.getMessageType());  
       message.setMessageState(MessageState.SENT);  

       messageRespository.save(message); 

        log.info("Message Saved Successfully!");

        // TODO: Implement Notifications






    }


    @Override
    public List<MessageDTO> findChatMessages(String chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with Chat Id: " + chatId + " not found")); 
        List<Message> messages = messageRespository.findByChat(chat); 


        return messageMapper.toDtoList(messages); 

    }

    @Override
    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with Chat Id: " + chatId + " Not found "));

        final String recipientId = getRecipientId(chat, authentication); 

        messageRespository.setMessagesToSeenByChatId(chatId, MessageState.SEEN); 

        // TODO: Send Notification

    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getUserId().equals(authentication.getName())) {
            return chat.getRecipient().getUserId(); 
        }
        return chat.getSender().getUserId(); 

    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {

         Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with Chat Id: " + chatId + " Not found "));
        

    }
    
}
