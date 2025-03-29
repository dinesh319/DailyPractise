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
public class LoginResponseDto {

    private Long userId;

    @NotBlank(message = "access token cannot be left blank")
    private String accessToken;

    @NotBlank(message = "refresh token cannot be left blank")
    private String refreshToken;
}
