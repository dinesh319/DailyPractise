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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError error = ApiError.builder()
                .message(resourceNotFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException.getBindingResult()
                .getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).toList();

        ApiError apiError = ApiError.builder().message("validation failed").status(HttpStatus.BAD_REQUEST).subErrors(errors).build();


            return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
    }

    // for generic exception handling

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInternalExceptions(Exception exception){
        ApiError apiError = ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
        return new ResponseEntity<>(apiError , HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
