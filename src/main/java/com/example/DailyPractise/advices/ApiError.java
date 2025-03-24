package com.example.DailyPractise.advices;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
@Setter
public class ApiError {

    private String message;

    private List<String> subMessage;

    private HttpStatus status;

}
