package org.mehmood.blogapplicationbackendproject.exceptions;

import org.mehmood.blogapplicationbackendproject.payLoads.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
        String message= resourceNotFoundException.getMessage();
        CustomApiResponse apiResponse=new CustomApiResponse(message,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
