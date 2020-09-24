package com.moortahc.server.room.controller;

import com.moortahc.server.room.config.RoomConfig;
import com.moortahc.server.room.sse.SseEmitters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RoomController implements InitializingBean {

    private final Map<String, SseEmitters> roomIdToSseEmitters;

    @Autowired
    public RoomController(Map<String, SseEmitters> roomIdToSseEmitters) {
        this.roomIdToSseEmitters = roomIdToSseEmitters;
    }

    @GetMapping(path = "/listen/{roomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter listen(@PathVariable String roomId) {
        log.info("ROOMID {}", roomId);
        var sseEmitter = new SseEmitter();
        var sseEmitters = roomIdToSseEmitters.computeIfAbsent(roomId, key -> new SseEmitters());
        sseEmitters.add(sseEmitter);
        sseEmitters.send("HELLO");System.out.println(sseEmitters == roomIdToSseEmitters.get(roomId));
        log.info("{} GRIIOR", roomIdToSseEmitters.size());
        return sseEmitter;
    }

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("HOME", HttpStatus.OK);
    }

    @GetMapping("/ticks")
    public SseEmitter handle(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        var sseEmitter = new SseEmitter();
        roomIdToSseEmitters.computeIfAbsent("0", key -> new SseEmitters()).add(sseEmitter);

        log.info("TEST");
        return sseEmitter;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        RoomConfig.SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
            log.info("{}", roomIdToSseEmitters.size());
            new HashMap<>(roomIdToSseEmitters).values().forEach(sseEmitters -> {
                sseEmitters.send("HELLO");
            });
        }, 0, 1, TimeUnit.SECONDS);
    }
}
