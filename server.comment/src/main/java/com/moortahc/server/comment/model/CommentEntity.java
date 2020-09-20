package com.moortahc.server.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity implements Comment {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String fromId;
    
    private String postId;
    
    private String content;
    
    private Instant createdDate;
}
