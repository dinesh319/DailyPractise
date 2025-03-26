package com.example.DailyPractise.advices;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<?>> buildResponseEntityWithError(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError) , apiError.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException authenticationException){
        ApiError apiError = ApiError.builder()
                .message(authenticationException.getMessage()).status(HttpStatus.UNAUTHORIZED)
                .build();

        return buildResponseEntityWithError(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException jwtException){

        HttpStatus status = jwtException instanceof ExpiredJwtException
                ? HttpStatus.UNAUTHORIZED // Expired token -> authentication failure
                : HttpStatus.FORBIDDEN;

        ApiError apiError = ApiError.builder()
                .message(jwtException.getMessage()).status(status)
                .build();

        return buildResponseEntityWithError(apiError);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentsNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException
                .getBindingResult().getAllErrors()
                .stream().map(err -> err.getDefaultMessage()).collect(Collectors.toUnmodifiableList());

        ApiError apiError = ApiError.builder()
                .message("validation failed").subMessage(errors).status(HttpStatus.BAD_REQUEST)
                .build();
        return buildResponseEntityWithError(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError apiError = ApiError.builder()
                .message(resourceNotFoundException.getMessage()).status(HttpStatus.NOT_FOUND)
                .build();
        return buildResponseEntityWithError(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception){
        ApiError apiError = ApiError.builder()
                .message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return buildResponseEntityWithError(apiError);
    }


}
