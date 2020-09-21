package com.moortahc.server.comment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableEurekaClient
@EnableDiscoveryClient
@ComponentScan("com.moortahc.server")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CommentDriver {
    
    public static void main(String[] args) {
        SpringApplication.run(CommentDriver.class, args);
    }
    
}