package com.example.DailyPractise.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {

    private Long userId;

    private String accessToken;

    private String refreshToken;

}
