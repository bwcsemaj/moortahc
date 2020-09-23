package com.moortahc.server.post.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto implements Post{
    
    private Long id;
    private Long fromId;
    private String content;
    private Instant createdDate;
    private String roomName;
    
    public String convertToMessageJSON() {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            return objectMapper.writeValueAsString(MessageDto.builder()
                    .postId(id)
                    .fromId(fromId)
                    .content(content)
                    .createdDate(createdDate)
                    .roomName(roomName)
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
