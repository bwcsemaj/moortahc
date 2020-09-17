package com.moortahc.server.authenticate;

import com.moortahc.server.authenticate.config.UserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.moortahc.server")
public class AuthenticateDriver {
    
    public static void main(String[] args) {
        SpringApplication.run(AuthenticateDriver.class, args);
    }
    
}