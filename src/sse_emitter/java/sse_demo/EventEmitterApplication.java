package sse_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class EventEmitterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventEmitterApplication.class);
    }
}
