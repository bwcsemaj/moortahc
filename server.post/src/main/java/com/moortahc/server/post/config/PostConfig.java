package com.moortahc.server.post.config;

import com.moortahc.server.post.model.PostEntity;
import com.moortahc.server.post.repo.PostRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Configuration
public class PostConfig implements InitializingBean {
    
    private final PostRepository postRepository;
    
    @Autowired
    public PostConfig(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        postRepository.save(PostEntity.builder()
                .content("Hello")
                .fromId(0l)
                .createdDate(Instant.now())
                .roomName("roomName")
                .build());
    }
}
