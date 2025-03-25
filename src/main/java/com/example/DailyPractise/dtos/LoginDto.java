package com.example.DailyPractise.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "email field cannot be blank")
    @Email(message = "please enter proper email")
    private String email;

    @NotBlank(message = "password field cannot be blank")
    private String password;

}
