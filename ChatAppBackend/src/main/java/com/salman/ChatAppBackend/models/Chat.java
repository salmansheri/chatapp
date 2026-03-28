package com.salman.ChatAppBackend.models;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

import com.salman.ChatAppBackend.common.BaseAuditingEntity;
import com.salman.ChatAppBackend.enums.MessageState;
import com.salman.ChatAppBackend.enums.MessageType;
import com.salman.ChatAppBackend.lib.constants.ChatConstants;
import com.salman.ChatAppBackend.lib.constants.MessageConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chats")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID, 
    query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.userId = :senderId OR c.recipient.userId = :senderId ORDER BY createdDate DESC"
)
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVERID, 
    query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.userId = :senderId AND c.recipient.userId = :recipientId) OR (c.sender.userId = :recipientId AND c.recipient.userId = :senderId)"
)
public class Chat extends BaseAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String chatId; 


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender; 

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient; 

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages; 

    @Transient
    public String getChatName(final String senderId) {
        if (recipient.getUserId().equals(senderId)) {
            return sender.getFirstName() + " " + sender.getLastName(); 


        }

        return recipient.getFirstName() + " " + recipient.getLastName(); 

    }


    @Transient
    public long getUnreadmessages(final String senderId) {
        return messages.stream()
                    .filter(m-> m.getReceiverId().equals(senderId))
                    .filter(m -> MessageState.SENT == m.getMessageState())
                    .count();  
    }



    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            if (messages.get(0).getMessageType() != MessageType.TEXT) {
                return "Attachment"; 
            }

            return messages.get(0).getContent(); 
        }

        return null; 
    }

    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(0).getCreatedDate(); 
        }

        return null; 
    }

    


    
    
}
