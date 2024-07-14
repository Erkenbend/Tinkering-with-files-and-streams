package org.example.fileconsumerreactive;

import org.example.ApiClient;
import org.example.api.FileApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileConsumerReactiveApplication {

    @Value("${file-provider.url}")
    private String fileProviderUrl;

    public static void main(String[] args) {
        SpringApplication.run(FileConsumerReactiveApplication.class, args);
    }

    @Bean
    public ApiClient fileProviderApiClient() {
        return new ApiClient().setBasePath(fileProviderUrl);
    }

    @Bean
    public FileApi fileApi(ApiClient apiClient) {
        return new FileApi(apiClient);
    }
}
