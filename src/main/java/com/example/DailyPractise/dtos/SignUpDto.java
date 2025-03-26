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
public class SignUpDto {

    @NotBlank(message = "email cannot be left blank")
    @Email(message = "please provide proper format")
    private String email;

    @NotBlank(message = "password cannot be left blank")
    private String password;

    @NotBlank(message = "username cannot be left blank")
    private String username;
}
