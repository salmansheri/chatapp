package com.salman.ChatAppBackend.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salman.ChatAppBackend.DTOs.ApiResponseDTO;
import com.salman.ChatAppBackend.DTOs.MessageDTO;
import com.salman.ChatAppBackend.DTOs.MessageRequestDTO;
import com.salman.ChatAppBackend.DTOs.NotificationDTO;
import com.salman.ChatAppBackend.enums.MessageState;
import com.salman.ChatAppBackend.enums.MessageType;
import com.salman.ChatAppBackend.enums.NotificationType;
import com.salman.ChatAppBackend.interfaces.FileService;
import com.salman.ChatAppBackend.interfaces.MessageService;
import com.salman.ChatAppBackend.interfaces.NotificationService;
import com.salman.ChatAppBackend.lib.utils.FileUtils;
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
    private final FileService fileService;
    private final NotificationService notificationService;
    // private final MessageMapper messageMapper;

    @Override
    public MessageDTO saveMessage(MessageRequestDTO messageRequestDTO) {
        log.info("Saving message");
        Chat chat = chatRepository.findById(messageRequestDTO.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        Message message = new Message();

        message.setContent(messageRequestDTO.getContent());
        message.setChat(chat);
        message.setSenderId(messageRequestDTO.getSenderId());
        message.setReceiverId(messageRequestDTO.getReceiverId());
        message.setMessageType(messageRequestDTO.getMessageType());
        message.setMessageState(MessageState.SENT);

        Message savedMessage = messageRespository.save(message);

        log.info("Message Saved Successfully!");

        NotificationDTO notificationDTO = NotificationDTO.builder()
        .chatId(chat.getChatId())
        .messageType(messageRequestDTO.getMessageType())
        .content(messageRequestDTO.getContent())
        .senderId(messageRequestDTO.getSenderId())
        .receiverId(messageRequestDTO.getReceiverId())
        .notificationType(NotificationType.MESSAGE)
        .chatName(chat.getChatName(message.getSenderId()))
        .build(); 

        notificationService.sendNotification(message.getReceiverId(), notificationDTO); 





        return MessageMapper.toMessageResponse(savedMessage);

    }

    @Override
    public List<MessageDTO> findChatMessages(String chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat with Chat Id: " + chatId + " not found"));
        List<Message> messages = messageRespository.findByChat(chat);

        return messages
                .stream()
                .map(message -> MessageMapper.toMessageResponse(message))
                .toList();

    }

    @Override
    @Transactional
    public ApiResponseDTO setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat with Chat Id: " + chatId + " Not found "));

        final String recipientId = getRecipientId(chat, authentication);

        messageRespository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

         NotificationDTO notificationDTO = NotificationDTO.builder()
        .chatId(chat.getChatId()) 
       
        .senderId(getSenderId(chat, authentication))
        .receiverId(recipientId)
        .notificationType(NotificationType.SEEN)
     
        .build(); 

        notificationService.sendNotification(recipientId, notificationDTO); 

        ApiResponseDTO response = new ApiResponseDTO("Message set to seen!", true);

        return response;

    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getUserId().equals(authentication.getName())) {
            return chat.getRecipient().getUserId();
        }
        return chat.getSender().getUserId();

    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getUserId().equals(authentication.getName())) {
            return chat.getRecipient().getUserId();
        }
        return chat.getRecipient().getUserId();

    }

    @Override
    public ApiResponseDTO uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat with Chat Id: " + chatId + " Not found "));

        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);

        Message message = new Message();

        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setMessageType(MessageType.IMAGE);
        message.setMessageState(MessageState.SENT);
        message.setMediaFilePath(filePath);

        messageRespository.save(message);

          NotificationDTO notificationDTO = NotificationDTO.builder()
        .chatId(chat.getChatId())
        .messageType(MessageType.IMAGE)
      
        .senderId(senderId)
        .receiverId(recipientId)
        .notificationType(NotificationType.IMAGE)
      .media(FileUtils.readFileFromLocation(filePath))
        .build(); 

        notificationService.sendNotification(recipientId, notificationDTO); 

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO("File Uploaded Successfully", true);

        return apiResponseDTO;

    }

}
