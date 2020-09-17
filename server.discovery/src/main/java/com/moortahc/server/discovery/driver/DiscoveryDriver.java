package com.moortahc.server.discovery.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryDriver {
    
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryDriver.class, args);
    }
}