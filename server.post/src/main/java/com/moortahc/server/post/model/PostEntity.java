package com.moortahc.server.post.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class PostEntity implements Post{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long fromId;
    
    private String content;
    
    private Instant createdDate;
}
