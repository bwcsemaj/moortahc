package com.moortahc.server.authenticate.config;

import com.moortahc.server.authenticate.model.UserEntity;
import com.moortahc.server.authenticate.model.UserRole;
import com.moortahc.server.authenticate.repo.UserRepository;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class UserConfig implements InitializingBean, DisposableBean {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        userRepository.save(UserEntity
                .builder()
                .createdDate(LocalDateTime.now())
                .emailAddress("a")
                .firstName("Tom")
                .lastName("Jerry")
                .generatedName("GEN")
                .passwordHash(passwordEncoder.encode("password"))
                .id(0L)
                .roles(List.of(UserRole.USER))
                .build());
    }
    
    @Override
    public void destroy() throws Exception {
    
    }
}
