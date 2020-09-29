package com.moortahc.server.room.controller;

import com.moortahc.server.room.config.RoomConfig;
import com.moortahc.server.room.model.MessageDto;
import com.moortahc.server.room.service.RoomService;
import com.moortahc.server.room.sse.SseEmitters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RoomController {
    
    private final RoomService roomService;
    
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    
    @GetMapping(path = "/listen", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter listen(@RequestParam String roomName) {
        var sseEmitter = new SseEmitter();
        roomService.tryListenTo(roomName, sseEmitter);
        return sseEmitter;
    }
    
    @PostMapping("/dispatch")
    public void dispatchMessageToRoom(@RequestBody MessageDto messageDto) {
        roomService.dispatchMessage(messageDto);
    }
    
    
}
