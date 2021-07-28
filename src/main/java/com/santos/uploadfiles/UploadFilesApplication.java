package com.santos.uploadfiles;

import com.santos.uploadfiles.config.FileStorageConfig;
import com.santos.uploadfiles.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class UploadFilesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UploadFilesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
