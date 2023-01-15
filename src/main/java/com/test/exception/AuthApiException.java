package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AuthApiException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public AuthApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;

    }

    public AuthApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }



    public HttpStatus getStatus() {
        return status;
    }

}

