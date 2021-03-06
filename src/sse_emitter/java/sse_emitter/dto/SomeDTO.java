package sse_emitter.dto;

import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class SomeDTO {
    private final long id;

    public SomeDTO() {
        this.id = ThreadLocalRandom.current().nextLong(1000000, 9999999);
    }
}
