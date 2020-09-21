//package com.moortahc.server.comment.controller;
//
//import com.moortahc.server.comment.model.CommentDto;
//import com.moortahc.server.comment.model.CommentEntity;
//import com.moortahc.server.comment.service.CommentService;
//import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
//import com.moortahc.server.comment.service.exceptions.PostDNEException;
//import lombok.SneakyThrows;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class CommentController {
//
//    private final CommentService commentService;
//
//    @Autowired
//    public CommentController(CommentService commentService) {
//        this.commentService = commentService;
//    }
//
//
//    @PostMapping("/{postId}")
//    public ResponseEntity<CommentDto> create(@PathVariable Long postId, @RequestParam String content){
//        try {
//            try {
//                return new ResponseEntity<>(convertTo(commentService.tryCreateComment(content, postId)), HttpStatus.OK);
//            } catch (PostDNEException e) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (InvalidCommentException e) {
//           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//    public static CommentDto convertTo(CommentEntity commentEntity) {
//        var commentDto = new CommentDto();
//        BeanUtils.copyProperties(commentEntity, commentDto);
//        return commentDto;
//    }
//}
