package com.moortahc.server.room.model;

import java.time.Instant;
import java.util.Optional;

public interface Message {
    
    public Long getPostId();
    
    public Optional<Long> getCommentId();
    
    public String getContent();
    
    public Instant getCreatedDate();
}
