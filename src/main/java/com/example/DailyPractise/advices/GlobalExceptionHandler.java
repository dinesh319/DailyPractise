package com.example.DailyPractise.advices;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<?>> buildResponseWithError(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException authenticationException){
        ApiError apiError = ApiError.builder()
                .message(authenticationException.getLocalizedMessage()).status(HttpStatus.UNAUTHORIZED)
                .build();

        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException jwtException){
        HttpStatus status = (jwtException instanceof ExpiredJwtException)
                ? HttpStatus.FORBIDDEN
                : HttpStatus.UNAUTHORIZED;

        ApiError apiError = ApiError.builder()
                .message(jwtException.getLocalizedMessage()).status(status)
                .build();

        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){

        ApiError apiError = ApiError.builder()
                .message(resourceNotFoundException.getLocalizedMessage()).status(HttpStatus.NOT_FOUND)
                .build();

        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentsNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){

        List<String> errors = methodArgumentNotValidException
                .getBindingResult().getAllErrors().stream().map(err ->err.getDefaultMessage()).toList();

        ApiError apiError = ApiError.builder()
                .message("validation failed").subMessage(errors).status(HttpStatus.BAD_REQUEST)
                .build();

        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(Exception exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getLocalizedMessage()).status(HttpStatus.FORBIDDEN)
                .build();
        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getLocalizedMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return buildResponseWithError(apiError);
    }

}
