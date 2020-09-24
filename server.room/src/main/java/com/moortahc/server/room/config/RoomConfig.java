package com.moortahc.server.room.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moortahc.server.room.sse.SseEmitters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class RoomConfig  {

//    public static final ScheduledExecutorService SCHEDULED_EXECUTOR =
//            Executors.newScheduledThreadPool(Integer.MAX_VALUE, Thread.builder().virtual().factory());
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(10);

    private final Map<String, SseEmitters> roomIdToSseEmitters;

    @Autowired
    public RoomConfig(Map<String, SseEmitters> roomIdToSseEmitters) {
        this.roomIdToSseEmitters = roomIdToSseEmitters;
    }

    @Bean
    public Map<String, SseEmitters> roomIdToSseEmitters() {
        return new ConcurrentHashMap<>();
    }
    
}
