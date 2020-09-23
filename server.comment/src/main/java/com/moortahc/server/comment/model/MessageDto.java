package com.moortahc.server.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class MessageDto {
    
    private final Long postId;
    
    private final Long commentId;
    
    private final String content;
    
    private final Instant createdDate;
    
    private final String roomName;
    
    private final Long fromId;
}
