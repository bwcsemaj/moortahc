package com.moortahc.server.authenticate.controller;

import com.moortahc.server.authenticate.model.UserDto;
import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.repo.exception.UserDoesNotExistException;
import com.moortahc.server.authenticate.service.UserService;
import com.moortahc.server.authenticate.service.exceptions.InvalidUserCredentialsException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
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
    
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestParam String emailAddress, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName){
        try{
            var userEntity = userService.create(emailAddress, password, firstName, lastName);
            return new ResponseEntity<>(convertTo(userEntity), HttpStatus.OK);
        }   catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    public static UserDto convertTo(UserEntity userEntity) {
        var userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }
}
