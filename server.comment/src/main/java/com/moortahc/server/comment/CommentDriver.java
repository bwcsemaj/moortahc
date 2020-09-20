package com.moortahc.server.comment;

import com.moortahc.server.comment.model.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Driver;

@SpringBootApplication
@ComponentScan("com.moortahc.server")
public class CommentDriver {
    
    public static void main(String[] args) {
        SpringApplication.run(CommentDriver.class, args);
    }
    
}