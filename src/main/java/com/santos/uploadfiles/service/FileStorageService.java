package com.santos.uploadfiles.service;

import com.santos.uploadfiles.config.FileStorageConfig;
import com.santos.uploadfiles.exception.FileNotFoundException;
import com.santos.uploadfiles.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.lang.String.format;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e){
            throw new FileStorageException("""
                    Could not create the directory where the uploaded files will be storage
                    """.trim(), e);
        }
    }

    public String storageFile(MultipartFile file){
        var fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")){
                throw new FileStorageException(format("""
                    Sorry! Filename contains invalid path sequence %s
                    """.trim(), fileName));
            }

            var targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception e){
            throw new FileStorageException(format("""
                    Could not store file %s. Please try again!
                    """.trim(), fileName), e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try {
            var filePath = fileStorageLocation.resolve(fileName).normalize();
            var resource = new UrlResource(filePath.toUri());

            if(resource.exists())
                return resource;

            throw new FileNotFoundException(format("""
                    File not found %s
                    """.trim(), fileName));
        } catch (Exception e){
            throw new FileNotFoundException(format("""
                    File not found %s
                    """.trim(), fileName), e);
        }
    }
}
