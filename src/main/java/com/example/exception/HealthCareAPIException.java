package com.example.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class HealthCareAPIException extends RuntimeException {

  
	private HttpStatus status;
    private String message;

    public HealthCareAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HealthCareAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}