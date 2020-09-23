package com.moortahc.server.comment.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private Long fromId;
    private String content;
    private Instant createdDate;
    private String roomName;
    
}
