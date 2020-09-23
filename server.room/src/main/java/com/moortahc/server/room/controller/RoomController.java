package com.moortahc.server.room.controller;

import com.moortahc.server.room.model.MessageDto;
import com.moortahc.server.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RoomController {
    
    private final RoomService roomService;
    
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload MessageDto chatMessage) {
        return chatMessage;
    }
    
    @PutMapping("/dispatch")
    public void sendComment(@RequestBody MessageDto messageDto){
        log.info("GOT MESSAGE {}", messageDto);
        roomService.dispatchMessage(messageDto);
    }
}
