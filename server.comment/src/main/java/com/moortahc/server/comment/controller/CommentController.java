package com.moortahc.server.comment.controller;

import com.moortahc.server.comment.model.CommentDto;
import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.comment.service.CommentService;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import com.moortahc.server.comment.service.exceptions.PostDNEException;
import com.moortahc.server.common.security.JwtConfig;
import com.moortahc.server.common.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class CommentController {
    
    private final CommentService commentService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtConfig jwtConfig;
    
    @Autowired
    public CommentController(CommentService commentService, JwtTokenUtil jwtTokenUtil, JwtConfig jwtConfig) {
        this.commentService = commentService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtConfig = jwtConfig;
    }
    
    
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> create(HttpServletRequest request, @PathVariable Long postId, @RequestParam String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            var userId = Long.valueOf(jwtTokenUtil.getUsernameFromRequest(request));
            return new ResponseEntity<>(convertTo(commentService.tryCreateComment(userId, content, postId)), HttpStatus.OK);
        } catch (PostDNEException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InvalidCommentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    public static CommentDto convertTo(CommentEntity commentEntity) {
        var commentDto = new CommentDto();
        BeanUtils.copyProperties(commentEntity, commentDto);
        return commentDto;
    }
}
