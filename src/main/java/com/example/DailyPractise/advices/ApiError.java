package com.example.DailyPractise.advices;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class ApiError {

    private String message;

    private List<String> subMessage;

    private HttpStatus status;
}
