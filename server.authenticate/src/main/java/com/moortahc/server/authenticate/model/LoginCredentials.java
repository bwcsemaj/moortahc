package com.moortahc.server.authenticate.model;

public interface LoginCredentials {
    
    public String getEmailAddress();
    
    public String getPassword();
    
    public void setEmailAddress(String emailAddress);
    
    public void setPassword(String password);
}
