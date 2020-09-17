package com.moortahc.server.authenticate.model;

public enum UserRole {
    USER, ADMIN;
    
    public String toSpringRole() {
        return "ROLE_" + name().toString();
    }
    
}
