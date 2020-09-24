package com.moortahc.server.springcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.codec.ServerCodecConfigurer;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.moortahc.server")
public class SpringCloudGatewayDriver {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayDriver.class, args);
    }
    
    @Bean
    public DiscoveryClientRouteDefinitionLocator discoveryRoutes(
            DiscoveryClient dc,
            DiscoveryLocatorProperties dlprops) {
        return new DiscoveryClientRouteDefinitionLocator(dc, dlprops);
    }
    
}