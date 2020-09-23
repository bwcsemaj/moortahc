package com.moortahc.server.comment.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    
    public String convertToMessageJSON() {
        try {
            return new ObjectMapper().writeValueAsString(MessageDto.builder()
                    .commentId(id)
                    .fromId(fromId)
                    .content(content)
                    .postId(postId)
                    .createdDate(createdDate));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}