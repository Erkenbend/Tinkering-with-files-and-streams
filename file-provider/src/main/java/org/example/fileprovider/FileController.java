package org.example.fileprovider;

import org.example.api.FileApi;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class FileController implements FileApi {

    @Override
    public Mono<ResponseEntity<Resource>> getFile(
            Integer groupId,
            Integer fileId,
            ServerWebExchange exchange
    ) {
        return Mono.just(new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT));
    }
}
