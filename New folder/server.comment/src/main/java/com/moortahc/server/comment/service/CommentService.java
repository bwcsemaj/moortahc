//package com.moortahc.server.comment.service;
//
//import com.moortahc.server.comment.model.CommentEntity;
//import com.moortahc.server.comment.repo.CommentRepository;
//import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
//import com.moortahc.server.comment.service.exceptions.PostDNEException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class CommentService {
//
//    private final RestTemplate restTemplate;
//    private final CommentRepository commentRepository;
//    private final CommentValidator commentValidator;
//
//    @Autowired
//    public CommentService(RestTemplate restTemplate, CommentRepository commentRepository, CommentValidator commentValidator) {
//        this.restTemplate = restTemplate;
//        this.commentRepository = commentRepository;
//        this.commentValidator = commentValidator;
//    }
//
//    public CommentEntity tryCreateComment(String content, Long postId) throws InvalidCommentException, PostDNEException {
//        //Validate comment
//        var verifiedCommentEntity = commentValidator.validate(content, postId);
//
//        //Save Comment
//        var savedCommentEntity = commentRepository.save(verifiedCommentEntity);
//
//        //Add comment to Message Broker
//        //#TODO add comment to message broker
//
//
//        return savedCommentEntity;
//    }
//}
