package com.example.DailyPractise.advices;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private ResponseEntity<ApiResponse<?>> buildResponseEntity(ApiError apiError) {

        return new ResponseEntity<>(new ApiResponse<>(apiError) , apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentsNotValid(MethodArgumentNotValidException exception){
        List<String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream().map(errorObj-> errorObj.getDefaultMessage()).toList();

        ApiError apiError = ApiError.builder()
                .message("validation failed")
                .subError(errors)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

       return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return buildResponseEntity(apiError);
    }

}
