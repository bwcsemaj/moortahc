package com.moortahc.server.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.moortahc.server")
public class GatewayDriver {
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayDriver.class, args);
    }
    
}