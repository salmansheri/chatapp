package com.salman.ChatAppBackend.exceptions;

public class ApiException extends RuntimeException {

    public ApiException() {
        super("Something went wrong. Please Try Again"); 
    }

    public ApiException(String message) {
        super(message); 
    }
    
}
