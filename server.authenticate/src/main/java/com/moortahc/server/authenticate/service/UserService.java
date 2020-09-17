package com.moortahc.server.authenticate.service;

import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.exceptions.InvalidUserCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public UserEntity validateCredentials(String emailAddress, String password) throws InvalidUserCredentialsException, UserDoesNotExistException {
        var opUserEntity = userRepository.findByEmailAddress(emailAddress);
        if(opUserEntity.isEmpty()){
            throw new UserDoesNotExistException(String.format("User with %s, does not exist", emailAddress));
        }
        var userEntity = opUserEntity.get();
        if(bCryptPasswordEncoder.matches(password, userEntity.getPasswordHash())){
            return userEntity;
        }
        throw new InvalidUserCredentialsException("User credentials are invalid");
    }
}
