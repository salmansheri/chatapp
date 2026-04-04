package com.salman.ChatAppBackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salman.ChatAppBackend.enums.MessageState;
import com.salman.ChatAppBackend.lib.constants.MessageConstants;
import com.salman.ChatAppBackend.models.Chat;
import com.salman.ChatAppBackend.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChat(Chat chat);

    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
	void setMessagesToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState state); 
    
}
