package com.kaze2.sse_demo.controller;

import com.kaze2.sse_demo.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ping(@RequestParam String message) {
        log.info("PING-PONG: {}", message);
        return ResponseEntity.ok("PONG: " + message);
    }

    @GetMapping(path = "/stream")
    public Flux<ServerSentEvent<String>> streamEvents() {
        log.info("Received request to stream events");
        return eventService.consumeStream()
                .map(s -> {
                    log.info("DATA: {}", s.data());
                    return ServerSentEvent.<String> builder()
                            .id("PROXY::" + Optional.ofNullable(s.id()).orElse("UNKNOWN_ID"))
                            .event("PROXY::" + Optional.ofNullable(s.event()).orElse("UNKNOWN_EVENT"))
                            .data("PROXY::" + s.data())
                            .build();
                });
    }

    @GetMapping(path = "/stream/item")
    public Flux<ServerSentEvent<String>> streamRequest() {
        log.info("Received request to stream events from non-SSE source");
        return eventService.createStream()
                .map(s -> {
                    log.info("DATA: {}", s.data());
                    return ServerSentEvent.<String> builder()
                            .id("PROXY::" + Optional.ofNullable(s.id()).orElse("UNKNOWN_ID"))
                            .event("PROXY::" + Optional.ofNullable(s.event()).orElse("UNKNOWN_EVENT"))
                            .data("PROXY::" + s.data())
                            .build();
                });
    }
}
