package com.santos.uploadfiles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException{

    public FileNotFoundException(String exception) {
        super(exception);
    }

    public FileNotFoundException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
