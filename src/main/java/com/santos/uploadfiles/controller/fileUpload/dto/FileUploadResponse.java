package com.santos.uploadfiles.controller.fileUpload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileUploadResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long fileSize;

}
