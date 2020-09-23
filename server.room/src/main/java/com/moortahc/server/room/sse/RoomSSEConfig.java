//package com.moortahc.server.room.config;
//
//import com.moortahc.server.room.controller.RoomController;
//import com.moortahc.server.room.service.SseEmitters;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.*;
//
//@Configuration
//public class RoomSSEConfig implements InitializingBean {
//
////    public static final ScheduledExecutorService SCHEDULED_EXECUTOR =
////            Executors.newScheduledThreadPool(Integer.MAX_VALUE, Thread.builder().virtual().factory());
//    public static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(10);
//
//    private final Map<String, SseEmitters> roomIdToSseEmitters;
//
//    @Autowired
//    public RoomSSEConfig(Map<String, SseEmitters> roomIdToSseEmitters) {
//        this.roomIdToSseEmitters = roomIdToSseEmitters;
//    }
//
//    @Bean
//    public Map<String, SseEmitters> roomIdToSseEmitters() {
//        return new ConcurrentHashMap<>();
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
//            new HashMap<>(roomIdToSseEmitters).values().forEach(sseEmitters -> {
//                sseEmitters.send("HELLO");
//            });
//        }, 0, 1, TimeUnit.SECONDS);
//    }
//}
