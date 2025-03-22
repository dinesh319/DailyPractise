package com.example.DailyPractise.advices;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiResponse<ApiError> buildApiResponseWithApiError(ApiError apiError) {
        return new ApiResponse<>(apiError);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<ApiError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError apiError = ApiError.builder()
                .message(resourceNotFoundException.getMessage()).status(HttpStatus.NOT_FOUND)
                .build();
        return buildApiResponseWithApiError(apiError);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ApiError> handleMethodArgumentsNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException.getBindingResult()
                .getAllErrors().stream()
                .map(err -> err.getDefaultMessage()).toList();

        ApiError apiError = ApiError.builder()
                .message("validation failed").subMessages(errors).status(HttpStatus.BAD_REQUEST)
                .build();

        return buildApiResponseWithApiError(apiError);
    }


//    @ExceptionHandler(BadCredentialsException.class)
//    public ApiResponse<ApiError> handleBadCredentialException(BadCredentialsException badCredentialsException){
//        ApiError apiError = ApiError.builder()
//                .message(badCredentialsException.getMessage()).status(HttpStatus.CONFLICT)
//                .build();
//       return buildApiResponseWithApiError(apiError);
//    }


    @ExceptionHandler(Exception.class)
    public ApiResponse<ApiError> handleException(Exception exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return buildApiResponseWithApiError(apiError);
    }


}
