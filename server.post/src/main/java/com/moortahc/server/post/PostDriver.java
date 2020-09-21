package com.moortahc.server.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@EnableDiscoveryClient
@ComponentScan("com.moortahc.server")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PostDriver {
    
    public static void main(String[] args) {
        SpringApplication.run(PostDriver.class, args);
    }
    
}