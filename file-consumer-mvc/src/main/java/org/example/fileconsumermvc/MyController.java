package org.example.fileconsumermvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.api.FileApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MyController {

    private final FileApi fileApi;

    @GetMapping("/trigger/{groupId}")
    public ResponseEntity<Integer> triggerFileDownload(@PathVariable Integer groupId) {
        return fileApi.getFilesInfo(groupId)
                .doOnNext(fileInfo -> log.info("Downloading file {}-{} ({} bytes)", groupId, fileInfo.getId(), fileInfo.getContentLength()))
                .flatMap(fileInfo -> fileApi.getFile(groupId, fileInfo.getId()))
                .map(fileContent -> fileContent.length)
                .reduce(Integer::sum)
                .map(ResponseEntity::ok)
                .block();
    }
}
