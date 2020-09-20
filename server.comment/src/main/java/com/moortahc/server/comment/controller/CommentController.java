package com.moortahc.server.comment.controller;

import com.moortahc.server.comment.model.CommentDto;
import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.comment.service.CommentService;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;
    
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    
    @PostMapping("/{roomId}/{postId}/")
    public ResponseEntity<CommentDto> createComment(@PathVariable String roomId, @PathVariable String postId, @RequestBody CommentDto commentDto){
        try {
            return new ResponseEntity<>(commentService.tryCreateComment(commentDto, postId, roomId), HttpStatus.MULTI_STATUS);
        } catch (InvalidCommentException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static CommentDto convertTo(CommentEntity commentEntity) {
        var commentDto = new CommentDto();
        BeanUtils.copyProperties(commentEntity, commentDto);
        return commentDto;
    }
}
