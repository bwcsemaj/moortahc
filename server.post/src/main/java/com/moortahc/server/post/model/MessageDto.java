package com.moortahc.server.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class MessageDto {
    
    private final Long postId;
    
    private final String content;
    
    private final String createdDate;
    
    private final String roomName;
    
    private final Long fromId;
    
}
