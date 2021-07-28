package com.santos.uploadfiles.controller.s3FileUpload;

import com.santos.uploadfiles.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/s3/upload")
@RequiredArgsConstructor
public class S3UploadFileController {

    private final S3Service service;

    @PostMapping
    public ResponseEntity<URI> uploadFile(@RequestParam MultipartFile file){
        var uri = service.uploadFile(file);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<URI> duplicate(@RequestParam String url){
        var uri = service.duplicateFile(url);
        return ResponseEntity.created(uri).build();
    }
}
