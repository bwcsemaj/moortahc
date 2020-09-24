package com.moortahc.server.gateway.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.moortahc.server")
@EnableZuulProxy
public class GatewayDriver {

    public static void main(String[] args) {
        SpringApplication.run(GatewayDriver.class, args);
    }

}