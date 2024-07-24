package org.example.fileprovider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.fileprovider.api.model.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileService {
    @Value("${app.delay-between-files-millis}")
    private Integer delayBetweenFilesMillis;

    @Value("${app.standard-file-size-bytes}")
    private Integer fileSizeBytes;

    private final Random random;

    public Flux<FileInfo> getFiles(Integer groupId) {
        Instant startTime = Instant.now();

        return Flux.range(1, groupId)
                .zipWith(Flux.interval(Duration.ofMillis(delayBetweenFilesMillis)))
                .map(Tuple2::getT1)
                .map(this::getFileInfo)
                .doOnComplete(() -> log.info("All files fetched in {}ms", Duration.between(startTime, Instant.now()).toMillis()));
    }

    public Mono<byte[]> getFile(Integer groupId, Integer fileId) {
        byte[] fileContent = new byte[fileSizeBytes];
        random.nextBytes(fileContent);
        return Mono.just(fileContent);
    }

    private FileInfo getFileInfo(Integer i) {
        log.info("Fetching file info {}", i);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(i);
        fileInfo.setContentLength(fileSizeBytes.longValue());
        return fileInfo;
    }
}
