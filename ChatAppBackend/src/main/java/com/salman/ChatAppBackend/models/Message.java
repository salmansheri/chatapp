package com.salman.ChatAppBackend.models;

import com.salman.ChatAppBackend.common.BaseAuditingEntity;
import com.salman.ChatAppBackend.enums.MessageState;
import com.salman.ChatAppBackend.enums.MessageType;
import com.salman.ChatAppBackend.lib.constants.MessageConstants;
import com.salman.ChatAppBackend.lib.constants.UserConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
@NamedQuery(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID, 
    query = "SELECT m FROM Message m WHERE m.chat.chatId = :chatId ORDER BY m.createdDate"
)

@NamedQuery(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT, 
    query = "UPDATE  Message SET messageState = :newMessageState WHERE chat.chatId = :chatId"
)
@SuperBuilder
public class Message extends BaseAuditingEntity {

    @Id
    @SequenceGenerator(name = "msg_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    private Long messageId; 

    @Column(columnDefinition = "TEXT")
    private String content; 

    @Enumerated(EnumType.STRING)
    @Column(name = "message_state")
    private MessageState messageState; 



    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType; 

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat; 

    @Column(nullable = false, name="sender_id")
    private String senderId; 

    @Column(name = "receiver_id", nullable = false)
    private String receiverId; 

    @Column(name = "media_file_path")
    private String mediaFilePath; 

    
}
