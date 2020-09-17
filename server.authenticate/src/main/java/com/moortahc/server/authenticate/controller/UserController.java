package com.moortahc.server.authenticate.controller;

import com.moortahc.server.authenticate.model.UserDto;
import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.UserService;
import com.moortahc.server.authenticate.service.exceptions.InvalidUserCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class UserController {
    
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @GetMapping("/authenticate2")
    public ResponseEntity<UserDto> authenticate(@RequestParam String emailAddress, @RequestParam String password) {
        UserEntity opUserEntity;
        try {
            opUserEntity = userService.validateCredentials(emailAddress, password);
        } catch (InvalidUserCredentialsException | UserDoesNotExistException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(convertTo(opUserEntity), HttpStatus.OK);
    }
    
    
    public static UserDto convertTo(UserEntity userEntity) {
        var userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }
}
