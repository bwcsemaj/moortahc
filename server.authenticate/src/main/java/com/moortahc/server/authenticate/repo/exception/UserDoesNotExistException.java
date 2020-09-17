package com.moortahc.server.authenticate.repo.exception;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(String description) {
        super(description);
    }
}
