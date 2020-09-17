package com.moortahc.server.authenticate.service.exceptions;

public class InvalidUserCredentialsException extends Exception{
    public InvalidUserCredentialsException(String description) {
        super(description);
    }
}
