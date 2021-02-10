package com.kaze2.sse_demo.service;

import com.kaze2.sse_demo.dto.SomeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@Slf4j
public class EventService {
    private final WebClient client;

    public EventService() {
        client = WebClient.create("http://localhost:8099");
    }

    public Flux<ServerSentEvent<String>> consumeStream() {
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<>() {};

        return client.get()
                .uri("/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(type);
    }

    public Flux<SomeDTO> createStream() {
        ParameterizedTypeReference<SomeDTO> type = new ParameterizedTypeReference<>() {};

        return client.get()
                .uri("/item")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(type)
                .delaySubscription(Duration.ofSeconds(1))
                .repeat();
    }
}
