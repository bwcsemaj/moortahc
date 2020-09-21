package com.moortahc.server.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableEurekaClient
@SpringBootApplication
//@ComponentScan("com.moortahc.server")
public class CommentDriver {
    
    public static void main(String[] args) {
        SpringApplication.run(CommentDriver.class, args);
    }
    
}