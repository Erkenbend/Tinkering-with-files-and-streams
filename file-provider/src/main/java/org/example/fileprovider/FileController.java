package org.example.fileprovider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.api.FileApi;
import org.example.api.model.FileInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Log4j2
public class FileController implements FileApi {

    private final FileService fileService;

    @Override
    public Mono<ResponseEntity<byte[]>> getFile(
            Integer groupId,
            Integer fileId,
            final ServerWebExchange exchange
    ) {
        return fileService.getFile(groupId, fileId)
                .doOnNext(fileContent -> log.info("Transmitting file {}-{} ({} bytes)", groupId, fileId, fileContent.length))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<FileInfo>>> getFilesInfo(
            Integer groupId,
            final ServerWebExchange exchange
    ) {
        return Mono.just(
                ResponseEntity.ok(fileService.getFiles(groupId))
        );
    }
}
