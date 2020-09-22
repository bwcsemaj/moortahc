package com.moortahc.server.authenticate.service;

import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.exceptions.InvalidUserCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
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
    
    @Transactional
    public UserEntity create(String emailAddress, String password, String firstName, String lastName) {
        //Validate
        if(!userValidator.validateUserInformation(emailAddress, password, firstName, lastName)){
           throw new IllegalArgumentException();
        }
    
        //See if user exist with email
        var opUserEntity = userRepository.findByEmailAddress(emailAddress);
        if(opUserEntity.isPresent()){
            throw new EntityExistsException();
        }
    
        //Save
        return userRepository.save(UserEntity.builder()
                .createdDate(Instant.now())
                .emailAddress(emailAddress)
                .passwordHash(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .generatedName(UUID.randomUUID().toString())
                .build());
    }
}
