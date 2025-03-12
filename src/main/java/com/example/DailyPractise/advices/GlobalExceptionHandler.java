package com.example.DailyPractise.advices;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private ApiResponse<ApiError> buildResponseWithError(ApiError apiError) {
        return new ApiResponse<>(apiError);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<ApiError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(resourceNotFoundException.getMessage())
                .build();
        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){

        List<String> errors  = methodArgumentNotValidException.
                getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage()).toList();

        ApiError apiError = ApiError.builder()
                .message("validation failed").subMessages(errors).status(HttpStatus.BAD_REQUEST)
                .build();

        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<ApiError> handleException(Exception exception){

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage())
                .build();

        return buildResponseWithError(apiError);

    }


}
