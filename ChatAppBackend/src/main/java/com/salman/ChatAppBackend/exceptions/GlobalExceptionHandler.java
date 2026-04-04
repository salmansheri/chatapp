package com.salman.ChatAppBackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salman.ChatAppBackend.DTOs.ApiResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<ApiResponseDTO> customApiException(ApiException ex) {
        String message = ex.getMessage(); 

        ApiResponseDTO apiResponseDTO = new ApiResponseDTO(message, false); 

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST); 
    }
    
}
