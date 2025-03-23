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
public class LogInDto {

    @NotBlank(message = "email cannot be left blank")
    private String email;

    @NotBlank(message = "password cannot be left blank")
    private String password;


}
