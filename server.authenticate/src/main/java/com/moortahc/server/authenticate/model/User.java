package com.moortahc.server.authenticate.model;

import java.time.LocalDateTime;
import java.util.List;

public interface User {
    
    /**
     * @return id associated with the user
     */
    public Long getId();
    
    /**
     * @return the date the user was created
     */
    public LocalDateTime getCreatedDate();
    
    /**
     * @return the first name of the user
     */
    public String getFirstName();
    
    /**
     * @return the last name of the user
     */
    public String getLastName();
    
    /**
     * @return the generated name for the user
     */
    public String getGeneratedName();
    
    /**
     * @return a List of roles the user has
     */
    public List<UserRole> getRoles();
}
