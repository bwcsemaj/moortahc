package com.moortahc.server.comment.service.commentvalidator;

import com.moortahc.server.comment.CommentDriver;
import com.moortahc.server.comment.model.CommentDto;
import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.comment.model.PostDto;
import com.moortahc.server.comment.service.CommentValidator;
import com.moortahc.server.comment.service.exceptions.InvalidCommentException;
import com.moortahc.server.comment.service.exceptions.PostDNEException;
import com.moortahc.server.common.security.JwtTokenUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommentDriver.class)
@ActiveProfiles("test")
@ComponentScan(basePackages = {"com.moortahc"})
public class ValidateTest {
    
    @Autowired
    private CommentValidator commentValidator;
    
    @MockBean
    private RestTemplate restTemplateMock;
    
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    
    @Test
    public void givenValidCommentThenCreateEntityTest() throws InvalidCommentException, PostDNEException {
        //given
        var givenFromId = 0l;
        var givenContent = "content";
        var givenPostId = 0l;
        var expectedPostDto = new PostDto();
        var givenURL = String.format("http://server-post/findById?postId=%s", String.valueOf(givenPostId));
        var expectedCommentDto = CommentEntity.builder()
                .postId(givenPostId)
                .content(givenContent)
                .fromId(givenFromId)
                .createdDate(Instant.now())
                .build();
        Mockito.when(restTemplateMock.getForObject(givenURL, PostDto.class)).thenReturn(expectedPostDto);
        
        //when
        var actualCommentDto = commentValidator.validate(givenFromId, givenContent, givenPostId);
        //lets assume the time created is what is expected
        actualCommentDto.setCreatedDate(expectedCommentDto.getCreatedDate());
        
        //then
        Assert.assertEquals(expectedCommentDto, actualCommentDto);
        verify(restTemplateMock, times(1))
                .getForObject(givenURL, PostDto.class);
    }
    
    @ParameterizedTest()
    @ValueSource(strings = {"", "   ", "oigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgareoigreaijoargeiojgare"})
    public void givenInvalidCommentThenThrowInvalidCommentExceptionTest(String invalidContent) throws InvalidCommentException, PostDNEException {
        //given
        var givenFromId = 0l;
        var givenPostId = 0l;
        var expectedPostDto = new PostDto();
        var givenURL = String.format("http://server-post/findById?postId=%s", String.valueOf(givenPostId));
        var expectedCommentDto = CommentEntity.builder()
                .postId(givenPostId)
                .content(invalidContent)
                .fromId(givenFromId)
                .createdDate(Instant.now())
                .build();
        Mockito.when(restTemplateMock.getForObject(givenURL, PostDto.class)).thenReturn(expectedPostDto);
        
        //when
        try{
            var actualCommentDto = commentValidator.validate(givenFromId, invalidContent, givenPostId);
        }   catch(InvalidCommentException exception){
            return;
        }   catch(Exception e){
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
        
        //then
        //throw exception
    }
    
    @Test
    public void givenInvalidPostIdThenThrowPostDNEExceptionTest() throws InvalidCommentException, PostDNEException {
        //given
        var givenFromId = 0l;
        var givenContent = "content";
        var givenPostId = 0l;
        var expectedPostDto = new PostDto();
        var givenURL = String.format("http://server-post/findById?postId=%s", String.valueOf(givenPostId));
        var expectedCommentDto = CommentEntity.builder()
                .postId(givenPostId)
                .content(givenContent)
                .fromId(givenFromId)
                .createdDate(Instant.now())
                .build();
        Mockito.when(restTemplateMock.getForObject(givenURL, PostDto.class)).thenReturn(expectedPostDto);
        
        //when
        var actualCommentDto = commentValidator.validate(givenFromId, givenContent, givenPostId);
        //lets assume the time created is what is expected
        actualCommentDto.setCreatedDate(expectedCommentDto.getCreatedDate());
        
        //then
        Assert.assertEquals(expectedCommentDto, actualCommentDto);
        verify(restTemplateMock, times(1))
                .getForObject(givenURL, PostDto.class);
    }
}
