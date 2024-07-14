package org.example.fileconsumerreactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ComputeService {
    public Mono<Long> computeSize(byte[] fileContent) {
        return Mono.just((long) fileContent.length);
    }
}
