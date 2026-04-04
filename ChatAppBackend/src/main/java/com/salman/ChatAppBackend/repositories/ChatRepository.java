package com.salman.ChatAppBackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salman.ChatAppBackend.lib.constants.ChatConstants;
import com.salman.ChatAppBackend.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, String>{

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<Chat> findChatsBySenderId(@Param("senderId") String userId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVERID)
    Optional<Chat> findChatBySenderIdAndReceiverId(@Param("senderId") String senderId, @Param("receipientId")String receiverId);

}