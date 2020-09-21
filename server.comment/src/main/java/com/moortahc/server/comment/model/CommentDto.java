package com.moortahc.server.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}