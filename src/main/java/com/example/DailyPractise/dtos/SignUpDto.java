package com.example.DailyPractise.dtos;

import com.example.DailyPractise.entities.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @NotBlank(message = "email field cannot be left blank")
    @Email(message = "please provide proper email")
    private String email;

    @NotBlank(message = "password field cannot be left blank")
    private String password;

    @NotBlank(message = "username field cannot be left blank")
    private String username;

    private Set<Roles> roles;

}
