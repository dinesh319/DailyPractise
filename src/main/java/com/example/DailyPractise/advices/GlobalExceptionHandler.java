package com.example.DailyPractise.advices;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiResponse<ApiError> bulidApiResponseWithError(ApiError apiError) {
        return new ApiResponse<>(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .error(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return bulidApiResponseWithError(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ApiError> handleMethodArgumentsNotValidException(MethodArgumentNotValidException exception){
        List<String> errors = exception.
                getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage())
                .toList();

        ApiError apiError = ApiError.builder()
                .error("validation failed").status(HttpStatus.BAD_REQUEST).subErrors(errors)
                .build();

        return bulidApiResponseWithError(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ApiResponse<ApiError> handleException(Exception exception){
        ApiError apiError = ApiError.builder().
                error(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return bulidApiResponseWithError(apiError);
    }

}
