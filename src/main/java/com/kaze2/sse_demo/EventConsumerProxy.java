package com.kaze2.sse_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class EventConsumerProxy {
    public static void main(String[] args) {
        SpringApplication.run(EventConsumerProxy.class);
    }
}
