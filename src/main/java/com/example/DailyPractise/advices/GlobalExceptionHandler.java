package com.example.DailyPractise.advices;


import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<?>> buildResponseWithError(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException accessDeniedException){
        ApiError apiError = ApiError.builder()
                .message(accessDeniedException.getLocalizedMessage()).status(HttpStatus.FORBIDDEN)
                .build();

        return buildResponseWithError(apiError);
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

        String message = "Invalid token.";

        if (jwtException instanceof ExpiredJwtException) {
            message = "Token has expired. Please log in again.";
        } else if (jwtException instanceof MalformedJwtException) {
            message = "Malformed JWT token.";
        }

        ApiError apiError = ApiError.builder()
                .message(message).status(HttpStatus.UNAUTHORIZED)
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
                .getBindingResult().getAllErrors().stream()
                .map(err -> err.getDefaultMessage()).collect(Collectors.toUnmodifiableList());

        ApiError apiError = ApiError.builder()
                .message("validation failed").subMessage(errors).status(HttpStatus.BAD_REQUEST)
                .build();

        return buildResponseWithError(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e){
        ApiError apiError = ApiError.builder()
                .message(e.getLocalizedMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return buildResponseWithError(apiError);
    }


}
