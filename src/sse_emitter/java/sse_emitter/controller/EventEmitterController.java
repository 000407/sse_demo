package sse_emitter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import sse_emitter.dto.SomeDTO;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EventEmitterController {

    @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ping(@RequestParam String message) {
        log.info("PING-PONG: {}", message);
        return ResponseEntity.ok("PONG: " + message);
    }

    @GetMapping(path = "/stream")
    public Flux<ServerSentEvent<String>> streamEvents() {
        log.info("Received request to stream events");
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String> builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalDateTime.now().toString())
                        .build());
    }

    @GetMapping(path = "/item")
    public ResponseEntity<SomeDTO> singleEvent() {
        log.info("Received request for single event");
        return ResponseEntity.ok(new SomeDTO());
    }
}
