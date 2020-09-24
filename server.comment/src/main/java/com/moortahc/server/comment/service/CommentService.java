package com.moortahc.server.comment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moortahc.server.comment.controller.CommentController;
import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.comment.repo.CommentRepository;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import com.moortahc.server.comment.service.exceptions.PostDNEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {
    
    private final RestTemplate restTemplate;
    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;
    
    @Autowired
    public CommentService(RestTemplate restTemplate, CommentRepository commentRepository, CommentValidator commentValidator) {
        this.restTemplate = restTemplate;
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
    }
    
    public CommentEntity tryCreateComment(Long fromId, String content, Long postId) throws InvalidCommentException, PostDNEException {
        //Validate post
        var verifiedPostDto = commentValidator.validatePost(postId);
        
        //Validate comment
        var verifiedCommentEntity = commentValidator.validateComment(fromId, content, postId);
        
        //Save Comment
        var savedCommentEntity = commentRepository.save(verifiedCommentEntity);
        
        //Add comment to Message Broker
        //#TODO Down the line use RabbitMQ for message broker, in this case just directly call on micro service.
        var savedCommentDto = CommentController.convertTo(savedCommentEntity);
        System.out.println(savedCommentDto.convertToMessageJSON(verifiedPostDto));
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<String>(savedCommentDto.convertToMessageJSON(verifiedPostDto), headers);
        var responseEntity = restTemplate.
                postForEntity("http://server-room/dispatch", request, Void.class);
        
        return savedCommentEntity;
    }
}
