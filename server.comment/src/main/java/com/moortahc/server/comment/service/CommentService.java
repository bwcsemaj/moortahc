package com.moortahc.server.comment.service;

import com.moortahc.server.comment.model.CommentDto;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentService {
    
    private final RestTemplate restTemplate;
    
    @Autowired
    public CommentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public CommentDto tryCreateComment(CommentDto commentDto, String postId, String roomId) throws InvalidCommentException {
        //Validate comment
        //if(!validateComment(commentDto)){
       //     throw new InvalidCommentException();
       // }
        
        //See if the Post actually exists
        //restTemplate.getForObject("http://")
        
        //Save Comment
        
        //Add comment to Message Broker
        return null;
    }
}
