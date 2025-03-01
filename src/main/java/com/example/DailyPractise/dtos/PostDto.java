package com.example.DailyPractise.dtos;


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

    @NotBlank(message = "message cannot be left blank")
    private String message;

    @NotBlank(message = "description cannot be left blank")
    private String description;
}
