package org.example.fileconsumerreactive;

import lombok.RequiredArgsConstructor;
import org.example.api.FileApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class MyController {

    private final FileApi fileApi;

    @GetMapping("/trigger")
    public Mono<ResponseEntity<Integer>> triggerFileDownload() {
        return fileApi.getFile(1, 1)
                .map(file -> ResponseEntity.ok(file.length));
    }
}
