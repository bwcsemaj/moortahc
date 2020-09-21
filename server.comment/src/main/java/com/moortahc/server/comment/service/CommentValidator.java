package com.moortahc.server.comment.service;

import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.comment.model.PostDto;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import com.moortahc.server.comment.service.exceptions.PostDNEException;
import com.moortahc.server.common.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class CommentValidator {

    private final RestTemplate restTemplate;
    private final JwtTokenUtil jwtTokenUtil;
    private static final int MAX_CONTENT_LENGTH = 256;

    @Autowired
    public CommentValidator(RestTemplate restTemplate, JwtTokenUtil jwtTokenUtil) {
        this.restTemplate = restTemplate;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public CommentEntity validate(Long fromId, String content, Long postId) throws PostDNEException, InvalidCommentException {
        //Validate the post
        var opPost = Optional.ofNullable(restTemplate.getForObject(String.format("http://server-post/findById?postId=%s", postId), PostDto.class));
        //If Post doesn't exist than comment can't exist
        if(opPost.isEmpty()){
            throw new PostDNEException();
        }

        //Make sure content length is less than max
        if (content == null || content.isBlank() || content.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidCommentException();
        }
        return CommentEntity.builder()
                .postId(postId)
                .content(content)
                .fromId(fromId)
                .createdDate(Instant.now())
                .build();
    }
}
