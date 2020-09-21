package com.moortahc.server.comment.controller.commentcontroller;

import com.moortahc.server.comment.CommentDriver;
import com.moortahc.server.comment.controller.CommentController;
import com.moortahc.server.comment.model.CommentEntity;
import com.moortahc.server.common.utility.MoorTahcUtility;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = CommentDriver.class)
@ActiveProfiles("test")
public class ConvertToTest {
    
    @Autowired
    private CommentController commentController;
    
    public static List<CommentEntity> givenUserEntityWhenConvertingThenAllFieldsSameTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return List.of(
                CommentEntity.builder()
                        .createdDate(Instant.now())
                        .content("content")
                        .fromId(1l)
                        .postId(99l).build(),
                CommentEntity.builder()
                        .createdDate(Instant.now())
                        .content("content34taerggare")
                        .fromId(1334434334l)
                        .postId(99344343l).build(),
                CommentEntity.builder()
                        .createdDate(Instant.now())
                        .content("contentgeragareaegrzzzz")
                        .fromId(1333l)
                        .postId(99888888888l).build(),
                new CommentEntity());
    }
    
    @ParameterizedTest
    @MethodSource
    public void givenCommentEntityWhenConvertingThenAllFieldsSameTest(CommentEntity commentEntity) throws IllegalAccessException {
        //given
        var givenCommentEntity = commentEntity;
        
        //when
        var actualCommentDto = CommentController.convertTo(givenCommentEntity);
        
        //then
        Assert.assertTrue(MoorTahcUtility.checkSameFieldValues(actualCommentDto, givenCommentEntity));
    }
}