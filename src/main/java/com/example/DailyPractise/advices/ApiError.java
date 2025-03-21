package com.example.DailyPractise.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private List<String> subMessages;

    @JsonFormat(pattern = "hh:MM:ss dd-mm-YYYY")
    private HttpStatus status;
}
