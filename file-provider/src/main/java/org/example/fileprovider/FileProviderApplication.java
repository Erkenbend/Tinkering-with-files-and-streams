package org.example.fileprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class FileProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileProviderApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
