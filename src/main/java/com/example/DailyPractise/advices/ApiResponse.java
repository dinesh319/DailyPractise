package com.example.DailyPractise.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private T data;

    private ApiError apiError;

    @JsonFormat(pattern = "dd:MM:yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp =  LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError apiError) {
        this();
        this.apiError = apiError;
    }
}
