package com.example.DailyPractise.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostDto {

//    private Long id;

    @NotBlank(message = "message cannot be blank")
    private String message;

    @NotBlank(message = "description cannot be blank")
    private String description;


}
