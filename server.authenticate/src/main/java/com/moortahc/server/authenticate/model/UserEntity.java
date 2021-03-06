package com.moortahc.server.authenticate.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserEntity implements User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Instant createdDate;
    
    private String emailAddress;
    
    /**
     * $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
     * \__/\/ \____________________/\_____________________________/
     *  Alg Cost      Salt                        Hash
     */
    private String passwordHash;
    
    private String firstName;
    
    private String lastName;
    
    private String generatedName;
    
    // Eager is needed https://stackoverflow.com/questions/416208/jpa-map-collection-of-enums
    @ElementCollection(fetch = FetchType.EAGER)
    private List<UserRole> roles;
    
}
