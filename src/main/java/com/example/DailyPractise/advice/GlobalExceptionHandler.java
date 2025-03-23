package com.example.DailyPractise.advice;


import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {



    private ResponseEntity<ApiResponse<?>> buildResponseEntityWithError(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError) , apiError.getStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>>  handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException.getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage()).toList();

        ApiError apiError = ApiError.builder()
                .message("validation failed").subMessages(errors).status(HttpStatus.BAD_REQUEST)
                .build();

       return  buildResponseEntityWithError(apiError);    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>>  handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError apiError = ApiError.builder()
                .message(resourceNotFoundException.getMessage()).status(HttpStatus.NOT_FOUND)
                .build();
       return  buildResponseEntityWithError(apiError);    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>>  handleException(Exception exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
       return  buildResponseEntityWithError(apiError);
    }




}
