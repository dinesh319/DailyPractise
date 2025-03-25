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
public class SignUpDto {

    @NotBlank(message = "email field cannot be left blank")
    private String email;

    @NotBlank(message = "password field cannot be left blank")
    private String password;

    @NotBlank(message = "username field cannot be left blank")
    private String username;

}
