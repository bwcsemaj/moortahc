package com.moortahc.server.authenticate.service;

import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.exceptions.InvalidUserCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserEntity validateCredentials(String emailAddress, String password) throws InvalidUserCredentialsException, UserDoesNotExistException {
        var opUserEntity = userRepository.findByEmailAddress(emailAddress);
        if(opUserEntity.isEmpty()){
            throw new UserDoesNotExistException(String.format("User with %s, does not exist", emailAddress));
        }
        var userEntity = opUserEntity.get();
        if(passwordEncoder.matches(password, userEntity.getPasswordHash())){
            return userEntity;
        }
        throw new InvalidUserCredentialsException("User credentials are invalid");
    }
}
