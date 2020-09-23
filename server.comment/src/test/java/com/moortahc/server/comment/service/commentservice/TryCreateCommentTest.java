package com.moortahc.server.comment.service.commentservice;

import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.comment.repo.CommentRepository;
import com.moortahc.server.comment.service.CommentService;
import com.moortahc.server.comment.service.CommentValidator;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import com.moortahc.server.comment.service.exceptions.PostDNEException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.moortahc"})
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TryCreateCommentTest {
    
    @MockBean
    private CommentValidator commentValidatorMock;
    
    @MockBean
    private CommentRepository commentRepositoryMock;
    
    @Autowired
    private CommentService commentService;
    
    @Test
    public void givenValidCommentThenGiveSavedEntityTest() throws InvalidCommentException, PostDNEException {
        //given
        var givenFromId = 1l;
        var givenContent = "LKGfioregiogr";
        var givenPostId = 1l;
        var expectedCommentEntity =  CommentEntity.builder()
                .createdDate(Instant.now())
                .content("content")
                .fromId(1l)
                .postId(99l).build();
        Mockito.when(commentValidatorMock.validateComment(givenFromId, givenContent, givenPostId)).thenReturn(expectedCommentEntity);
        Mockito.when(commentRepositoryMock.save(expectedCommentEntity)).thenReturn(expectedCommentEntity);
        
        //when
        var actualCommentEntity = commentService.tryCreateComment(givenFromId, givenContent, givenPostId);
        
        //then
        Assert.assertEquals(expectedCommentEntity, actualCommentEntity);
    }
    
    @Test(expected = InvalidCommentException.class)
    public void givenInvalidCommentThenThrowInvalidCommentException() throws InvalidCommentException, PostDNEException {
        //given
        var givenFromId = 1l;
        var givenContent = "LKGfioregiogr";
        var givenPostId = 1l;
        var expectedCommentEntity =  CommentEntity.builder()
                .createdDate(Instant.now())
                .content("content")
                .fromId(1l)
                .postId(99l).build();
        Mockito.when(commentValidatorMock.validateComment(givenFromId, givenContent, givenPostId)).thenThrow(new InvalidCommentException());
        Mockito.when(commentRepositoryMock.save(expectedCommentEntity)).thenReturn(expectedCommentEntity);
        
        //when
        var actualCommentEntity = commentService.tryCreateComment(givenFromId, givenContent, givenPostId);
        
        //then
        //throw exception
    }
    
    @Test(expected = PostDNEException.class)
    public void givenInvalidPostIdThenThrowInvalidCommentException() throws InvalidCommentException, PostDNEException {
        //given
        var givenFromId = 1l;
        var givenContent = "LKGfioregiogr";
        var givenPostId = 1l;
        var expectedCommentEntity =  CommentEntity.builder()
                .createdDate(Instant.now())
                .content("content")
                .fromId(1l)
                .postId(99l).build();
        Mockito.when(commentValidatorMock.validateComment(givenFromId, givenContent, givenPostId)).thenThrow(new PostDNEException());
        Mockito.when(commentRepositoryMock.save(expectedCommentEntity)).thenReturn(expectedCommentEntity);
        
        //when
        var actualCommentEntity = commentService.tryCreateComment(givenFromId, givenContent, givenPostId);
        
        //then
        //throw exception
    }
}
