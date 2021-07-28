package com.santos.uploadfiles.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;
    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile){
        try {
            var fileName = multipartFile.getOriginalFilename();
            var inputStream = multipartFile.getInputStream();
            var contentType = multipartFile.getContentType();

            return uploadFile(inputStream, fileName, contentType);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) throws URISyntaxException {
            var meta = new ObjectMetadata();
            meta.setContentType(contentType);
            s3Client.putObject(bucketName, fileName, inputStream, meta);
            return s3Client.getUrl(bucketName, fileName).toURI();
    }

    public URI duplicateFile(String url){
        try {
            var contentType = "";

            var response = getFileByUrl(url);
            var contentTypeHeader = response.headers().firstValue("content-type");
            if(contentTypeHeader.isPresent())
                contentType = contentTypeHeader.get();

            return uploadFile(response.body(), UUID.randomUUID().toString(), contentType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<InputStream> getFileByUrl(String url) throws URISyntaxException, IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(new URI(url))
                .GET()
                .build();
        var client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofInputStream());
    }
}
