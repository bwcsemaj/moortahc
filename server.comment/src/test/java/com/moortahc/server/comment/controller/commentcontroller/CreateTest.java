package com.moortahc.server.comment.controller.commentcontroller;

import com.moortahc.server.comment.controller.CommentController;
import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.comment.service.CommentService;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import com.moortahc.server.comment.service.exceptions.PostDNEException;
import com.moortahc.server.common.security.JwtConfig;
import com.moortahc.server.common.security.JwtTokenUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.moortahc"})
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreateTest {
    
    @Autowired
    private CommentController commentController;
    
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    
    @MockBean
    private JwtConfig jwtConfig;
    
    @MockBean
    private CommentService commentService;
    
    @Test
    public void givenValidCommentThenCommentSavedTest() throws PostDNEException, InvalidCommentException {
        //given
        var givenPostId = 0l;
        var givenContent = "Content";
        var givenUserId = 1l;
        var givenEntity = CommentEntity.builder()
                .content(givenContent)
                .fromId(givenUserId)
                .postId(givenPostId).build();
        Mockito.when(jwtTokenUtil.getUsernameFromRequest(null)).thenReturn(Long.toString(givenUserId));
        Mockito.when(commentService.tryCreateComment(givenUserId, givenContent, givenPostId))
                .thenReturn(givenEntity);
        
        //when
        var actualResponseEntity = commentController.create(null, givenPostId, givenContent);
        
        //then
        Assert.assertEquals(HttpStatus.OK.value(), actualResponseEntity.getStatusCodeValue());
        Assert.assertEquals(CommentController.convertTo(givenEntity), actualResponseEntity.getBody());
    }
    
    @Test(expected = InvalidCommentException.class)
    public void givenInvalidContentThenHTTPStatusNotFoundTest() throws PostDNEException, InvalidCommentException {
        //given
        var givenPostId = 0l;
        var givenUserId = 1l;
        var givenIncorrectContent = "";
        var givenEntity = CommentEntity.builder()
                .content(givenIncorrectContent)
                .fromId(givenUserId)
                .postId(givenPostId).build();
        Mockito.when(jwtTokenUtil.getUsernameFromRequest(null)).thenReturn(Long.toString(givenUserId));
        Mockito.when(commentService.tryCreateComment(givenUserId, givenIncorrectContent, givenPostId))
                .thenThrow(new InvalidCommentException());
        
        //when
        var actualResponseEntity =
                commentController.create(null, givenPostId, givenIncorrectContent);
        
        //then
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponseEntity.getStatusCodeValue());
    }
    
    @Test
    public void givenPostDNEThenHTTPStatusNotFoundTest() throws PostDNEException, InvalidCommentException {
        //given
        var givenInvalidPostId = 0l;
        var givenUserId = 1l;
        var givenContent = "";
        var givenEntity = CommentEntity.builder()
                .content(givenContent)
                .fromId(givenUserId)
                .postId(givenInvalidPostId).build();
        Mockito.when(jwtTokenUtil.getUsernameFromRequest(null)).thenReturn(Long.toString(givenUserId));
        Mockito.when(commentService.tryCreateComment(givenUserId, givenContent, givenInvalidPostId))
                .thenThrow(new PostDNEException());
    
        //when
        var actualResponseEntity = commentController.create(null, givenInvalidPostId, givenContent);
    
        //then
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), actualResponseEntity.getStatusCodeValue());
    }
}
