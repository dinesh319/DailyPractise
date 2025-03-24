package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
import com.example.DailyPractise.dtos.SignUpDto;
import jakarta.validation.Valid;

public interface AuthService {

    SignUpDto signUp(@Valid SignUpDto signUpData);

    LoginResponseDto login(@Valid LoginDto loginData);

    LoginResponseDto refreshTheToken(String refreshToken);
}
