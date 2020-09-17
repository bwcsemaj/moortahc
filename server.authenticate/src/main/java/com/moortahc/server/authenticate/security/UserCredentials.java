package com.moortahc.server.authenticate.security;

import com.moortahc.server.authenticate.model.LoginCredentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials implements LoginCredentials {
    private String emailAddress, password;
}