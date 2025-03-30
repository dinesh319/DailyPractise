package com.example.DailyPractise.dtos;

import com.example.DailyPractise.entities.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    @NotBlank(message = "message field cannot be left blank")
    private String message;

    @NotBlank(message = "description field cannot be left blank")
    private String description;

    private UserEntity author;
}
