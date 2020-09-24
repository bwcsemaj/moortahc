package com.moortahc.server.comment.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.moortahc.server.comment.controller.CommentController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.Message;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto implements Comment {

    private Long id;
    private Long fromId;
    private Long postId;
    private String content;
    private Instant createdDate;
    
    public String convertToMessageJSON(PostDto postDto) {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            return objectMapper.writeValueAsString(MessageDto.builder()
                    .commentId(id)
                    .fromId(fromId)
                    .content(content)
                    .postId(postId)
                    .createdDate(createdDate.toString())
                    .roomName(postDto.getRoomName())
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}