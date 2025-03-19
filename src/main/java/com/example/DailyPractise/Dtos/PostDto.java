package com.example.DailyPractise.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}
