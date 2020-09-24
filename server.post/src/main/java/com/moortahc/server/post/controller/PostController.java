package com.moortahc.server.post.controller;

import com.moortahc.server.common.security.JwtTokenUtil;
import com.moortahc.server.post.model.PostDto;
import com.moortahc.server.post.model.PostEntity;
import com.moortahc.server.post.service.PostService;
import com.moortahc.server.post.service.exceptions.PostDNEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class PostController {
    
    private final PostService postService;
    private final JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    public PostController(PostService postService, JwtTokenUtil jwtTokenUtil) {
        this.postService = postService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    
    @GetMapping("/findById")
    public ResponseEntity<PostDto> findById(@RequestParam Long postId){
        try {
            return new ResponseEntity<>(convertTo(postService.findById(postId)), HttpStatus.OK);
        } catch (PostDNEException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<PostDto> create(HttpServletRequest request, @RequestParam String roomName, @RequestParam String content){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            var userId = Long.valueOf(jwtTokenUtil.getUsernameFromRequest(request));
            return new ResponseEntity<>(convertTo(postService.tryCreatePost(userId, content, roomName)), HttpStatus.CREATED);
        }   catch (Exception e){
            // this will be replaced
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    
    public static PostDto convertTo(PostEntity postEntity) {
        var postDto = new PostDto();
        BeanUtils.copyProperties(postEntity, postDto);
        return postDto;
    }
}
