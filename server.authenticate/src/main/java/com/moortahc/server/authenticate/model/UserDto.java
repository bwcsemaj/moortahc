package com.moortahc.server.authenticate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements User {
    
    private Long id;
    private LocalDateTime createdDate;
    private String firstName;
    private String lastName;
    private String generatedName;
    private List<UserRole> roles;
}
