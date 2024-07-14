package org.example.fileconsumerreactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.fileprovider.api.FileApi;
import org.example.fileconsumer.api.TriggerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@Log4j2
public class TriggerController implements TriggerApi {

    private final FileApi fileApi;
    private final ComputeService computeService;

    @Override
    public Mono<ResponseEntity<Long>> trigger(
            Integer groupId,
            final ServerWebExchange exchange
    ) {
        return fileApi.getFilesInfo(groupId)
                .doOnNext(fileInfo -> log.info("Downloading file {}-{} ({} bytes)", groupId, fileInfo.getId(), fileInfo.getContentLength()))
                .flatMap(fileInfo -> fileApi.getFile(groupId, fileInfo.getId()))
                .flatMap(computeService::computeSize)
                .reduce(Long::sum)
                .map(ResponseEntity::ok);
    }
}
