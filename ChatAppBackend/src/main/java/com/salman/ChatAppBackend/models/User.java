package com.salman.ChatAppBackend.models;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

import com.salman.ChatAppBackend.common.BaseAuditingEntity;
import com.salman.ChatAppBackend.lib.constants.UserConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
// @NamedQuery(name = UserConstants.FIND_USER_BY_EMAIL, 
//     query = "SELECT u FROM User u WHERE u.email = :email"
// )
@NamedQuery(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF, 
    query = "SELECT u FROM User u WHERE u.userId != :publicId"
)
@NamedQuery(name = UserConstants.FIND_USER_BY_PUBLIC_ID, 
    query = "SELECT u FROM User u WHERE u.userId = :publicId"
)
@SuperBuilder
public class User extends BaseAuditingEntity {
    private static final int LAST_ACTIVE_INTERVAL = 5; 
    @Id
    private String userId; 
    private String firstName; 
    private String lastName; 
    private String email; 
    private LocalDateTime lastSeen; 

    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender; 

    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient; 

    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter((LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL))); 
    }

}
