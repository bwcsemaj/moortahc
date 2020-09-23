package com.moortahc.server.post.service;

import com.moortahc.server.post.model.PostEntity;
import com.moortahc.server.post.service.exceptions.InvalidPostException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PostValidator {
    
    public static PostEntity validate(Long userId, String roomName, String content) throws InvalidPostException {
        if(roomName == null || content == null || roomName.isEmpty() || content.isEmpty()){
            throw new InvalidPostException();
        }
        return PostEntity
                .builder()
                .createdDate(Instant.now())
                .fromId(userId)
                .content(content)
                .roomName(roomName)
                .build();
    }
}
