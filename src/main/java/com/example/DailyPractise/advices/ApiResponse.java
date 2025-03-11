package com.example.DailyPractise.advices;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private ApiError error;

    private T data;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }


    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }
}
