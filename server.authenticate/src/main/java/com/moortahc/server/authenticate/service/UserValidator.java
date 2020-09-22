package com.moortahc.server.authenticate.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    
    public static final int MIN_PASSWORD_LENGTH = 7;
    
    public boolean validateUserInformation(String emailAddress, String password, String firstName, String lastName){
        return isValidEmailAddress(emailAddress) && isValidPassword(password) && isValidFirstName(firstName) && isValidLastName(lastName);
    }
    
    public boolean isValidEmailAddress(String emailAddress){
        return EmailValidator.getInstance().isValid(emailAddress);
    }
    
    public boolean isValidPassword(String password){
        return password != null && password.length() > MIN_PASSWORD_LENGTH && !password.isBlank();
    }
    
    public boolean isValidFirstName(String firstName){
        return firstName != null && firstName.length() > 0 && !firstName.isBlank();
    }
    
    public boolean isValidLastName(String lastName){
        return lastName != null && lastName.length() > 0 && !lastName.isBlank();
    }
    
}
