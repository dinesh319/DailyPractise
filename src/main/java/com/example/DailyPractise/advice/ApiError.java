package com.example.DailyPractise.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class ApiError {

    private String message;

    private List<String> subMessages;

    private HttpStatus status;

}
