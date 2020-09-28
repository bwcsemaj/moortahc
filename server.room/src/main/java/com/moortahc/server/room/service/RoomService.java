package com.moortahc.server.room.service;

import com.moortahc.server.room.config.RoomConfig;
import com.moortahc.server.room.model.MessageDto;
import com.moortahc.server.room.sse.SseEmitters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RoomService implements InitializingBean {
    
    private final Map<String, SseEmitters> roomIdToSseEmitters;
    
    @Autowired
    public RoomService(Map<String, SseEmitters> roomIdToSseEmitters) {
        this.roomIdToSseEmitters = roomIdToSseEmitters;
    }
    
    public SseEmitter tryListenTo(String roomId, SseEmitter sseEmitter) {
        var sseEmitters = roomIdToSseEmitters.computeIfAbsent(roomId, key -> new SseEmitters());
        sseEmitters.add(sseEmitter);
        return sseEmitter;
    }
    
    public void dispatchMessage(MessageDto messageDto) {
        var opSseEmitters = Optional.ofNullable(roomIdToSseEmitters.get(messageDto.getRoomName()));
        opSseEmitters.ifPresent(sseEmitters -> sseEmitters.send(messageDto));
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
//        RoomConfig.SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
//            new HashMap<>(roomIdToSseEmitters).values().forEach(sseEmitters -> {
//                sseEmitters.send("HELLO");
//            });
//        }, 0, 10, TimeUnit.SECONDS);
    }
}
