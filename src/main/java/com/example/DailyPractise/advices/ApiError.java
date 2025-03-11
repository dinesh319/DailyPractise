package com.example.DailyPractise.advices;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String message;

    private List<String> subError;

    private HttpStatus status;
}
