package com.moortahc.server.room.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto implements Message {
    
    private Long postId;
    
    private Long commentId;
    
    private String content;
    
    private Instant createdDate;
    
    private String roomName;
    
    private Long fromId;
    
    public Optional<Long> getCommentId(){
        return Optional.ofNullable(commentId);
    }
}
