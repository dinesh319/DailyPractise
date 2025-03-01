package com.example.DailyPractise.advices;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<?>> buildResponseEntityWithError(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError) , apiError.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError apiError = ApiError.builder()
                .message(resourceNotFoundException.getMessage()).status(HttpStatus.NOT_FOUND)
                .build();
        return  buildResponseEntityWithError(apiError);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException
                .getBindingResult().getAllErrors()
                .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toUnmodifiableList());

        ApiError apiError = ApiError.builder()
                .message("validation failed").status(HttpStatus.BAD_REQUEST).subMessage(errors)
                .build();

        return  buildResponseEntityWithError(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return  buildResponseEntityWithError(apiError);
    }




}
