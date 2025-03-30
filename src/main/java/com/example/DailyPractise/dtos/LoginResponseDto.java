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

    @NotBlank(message = "accesstoken field cannot be left empty")
    private String accessToken ;

    @NotBlank(message = "refreshtoken field cannot be left empty")
    private String refreshToken ;

}
