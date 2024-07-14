package org.example.fileprovider;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.fileprovider.api.model.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileService {
    @Value("${app.delay-between-files-millis}")
    private Integer delayBetweenFiles;

    @Value("${app.standard-file-size-bytes}")
    private Integer fileSizeBytes;

    private final Random random;

    public Flux<FileInfo> getFiles(Integer groupId) {
        Instant startTime = Instant.now();

        return Flux
                .fromStream(
                        IntStream
                                .rangeClosed(1, groupId)
                                .mapToObj(this::getFileInfoSlowly)
                )
                .doOnComplete(() -> log.info("All files fetched in {}ms", Duration.between(startTime, Instant.now()).toMillis()));
    }

    @SneakyThrows(InterruptedException.class)
    private FileInfo getFileInfoSlowly(Integer i) {
        log.info("Fetching file info {}", i);
        Thread.sleep(Duration.ofMillis(delayBetweenFiles));

        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(i);
        fileInfo.setContentLength(fileSizeBytes.longValue());
        return fileInfo;
    }

    public Mono<byte[]> getFile(Integer groupId, Integer fileId) {
        byte[] fileContent = new byte[fileSizeBytes];
        random.nextBytes(fileContent);
        return Mono.just(fileContent);
    }
}
