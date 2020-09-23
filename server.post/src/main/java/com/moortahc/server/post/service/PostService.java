package com.moortahc.server.post.service;

import com.moortahc.server.post.model.PostEntity;
import com.moortahc.server.post.repo.PostRepository;
import com.moortahc.server.post.service.exceptions.PostDNEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    
    private final PostRepository postRepository;
    
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    public PostEntity findById(Long postId) throws PostDNEException {
        var opPostEntity = postRepository.findById(postId);
        if(opPostEntity.isEmpty()){
            throw new PostDNEException();
        }
        return postRepository.findById(postId).get();
    }
    
    
}
