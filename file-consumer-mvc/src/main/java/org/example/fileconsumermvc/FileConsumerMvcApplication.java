package org.example.fileconsumermvc;

import org.example.ApiClient;
import org.example.api.FileApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileConsumerMvcApplication {

    @Value("${file-provider.url}")
    private String fileProviderUrl;

    public static void main(String[] args) {
        SpringApplication.run(FileConsumerMvcApplication.class, args);
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
