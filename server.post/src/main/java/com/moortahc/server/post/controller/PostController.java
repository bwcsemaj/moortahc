package com.moortahc.server.post.controller;

import com.moortahc.server.post.model.Post;
import com.moortahc.server.post.model.PostDto;
import com.moortahc.server.post.model.PostEntity;
import com.moortahc.server.post.service.PostService;
import com.moortahc.server.post.service.exceptions.PostDNEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {
    
    private final PostService postService;
    
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @GetMapping("/findById")
    public ResponseEntity<PostDto> findById(@RequestParam Long postId){
        try {
            return new ResponseEntity<>(convertTo(postService.findById(postId)), HttpStatus.OK);
        } catch (PostDNEException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    public static PostDto convertTo(PostEntity postEntity) {
        var postDto = new PostDto();
        BeanUtils.copyProperties(postEntity, postDto);
        return postDto;
    }
}
