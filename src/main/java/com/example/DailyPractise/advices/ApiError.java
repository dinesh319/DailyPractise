package com.example.DailyPractise.advices;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    private String error;

    private List<String> subErrors;

    private HttpStatus status;
}
