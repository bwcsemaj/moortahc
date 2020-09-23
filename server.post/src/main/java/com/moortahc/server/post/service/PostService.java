package com.moortahc.server.post.service;

import com.moortahc.server.post.model.PostEntity;
import com.moortahc.server.post.repo.PostRepository;
import com.moortahc.server.post.service.exceptions.InvalidPostException;
import com.moortahc.server.post.service.exceptions.PostDNEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {
    
    private final PostRepository postRepository;
    private final RestTemplate restTemplate;
    private final PostValidator postValidator;
    
    @Autowired
    public PostService(PostRepository postRepository, RestTemplate restTemplate, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
        this.postValidator = postValidator;
    }
    
    public PostEntity findById(Long postId) throws PostDNEException {
        var opPostEntity = postRepository.findById(postId);
        if (opPostEntity.isEmpty()) {
            throw new PostDNEException();
        }
        return postRepository.findById(postId).get();
    }
    
    public PostEntity tryCreatePost(Long userId, String content, String roomName) throws InvalidPostException {
        //Validate Post
        var verifiedPostEntity = PostValidator.validate(userId, roomName, content);
        
        var postEntity = postRepository.save(verifiedPostEntity);
        
        //Send Post Entity to Room
        //restTemplate.put("http://server-room/post");
        
        return postEntity;
    }
    
    
}
