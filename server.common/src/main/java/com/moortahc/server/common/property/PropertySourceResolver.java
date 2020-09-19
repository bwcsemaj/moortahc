package com.moortahc.server.common.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertySourceResolver {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${application.properties.type}")
    private String applicationPropertiesType;

}