package com.moortahc.server.common.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtConfig {
    
    @Value("${security.jwt.uri:/authenticate/**}")
    private String uri;
    
    @Value("${security.jwt.header:Authorization}")
    private String header;
    
    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;
    
    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;
    
    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;
    
}
