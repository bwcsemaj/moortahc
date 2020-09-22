package com.moortahc.server.room.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;

@Controller
public class RoomController {
    
    
    @GetMapping(path = "/{roomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter create(@PathVariable String roomId){
    
    }
}
