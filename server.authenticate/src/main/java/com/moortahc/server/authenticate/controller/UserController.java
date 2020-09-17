package com.moortahc.server.authenticate.controller;

import com.moortahc.server.authenticate.model.UserDto;
import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.UserService;
import com.moortahc.server.authenticate.service.exceptions.InvalidUserCredentialsException;
import lombok.Getter;
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
    
    @Getter
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/authenticate2")
    public ResponseEntity<UserDto> authenticate(@RequestParam String emailAddress, @RequestParam String password) {
        UserEntity userEntity;
        try {
            userEntity = userService.validateCredentials(emailAddress, password);
        } catch (InvalidUserCredentialsException | UserDoesNotExistException e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(convertTo(userEntity), HttpStatus.OK);
    }
    
    
    public static UserDto convertTo(UserEntity userEntity) {
        var userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }
}
