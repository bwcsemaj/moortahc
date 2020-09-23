package com.moortahc.server.post.model;

import java.time.Instant;

public interface Post {
    
    /**
     * @return the id for post
     */
    public Long getId();
    
    /**
     * @return the User's id who created the post
     */
    public Long getFromId();
    
    /**
     * @return the Post's content (message/description)
     */
    public String getContent();
    
    /**
     * @return the Room name the post is made for
     */
    public String getRoomName();
    
    /**
     * @return the date the Post was created on
     */
    public Instant getCreatedDate();
}
