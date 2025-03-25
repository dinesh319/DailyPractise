package com.example.DailyPractise.controllers;

import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.services.implementations.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<SignUpDto> signUp(@RequestBody @Valid SignUpDto signUpData){
        SignUpDto savedData = authService.signUp(signUpData);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginData ,
                                                  HttpServletResponse response){

        LoginResponseDto loginResponseDto = authService.login(loginData);

        String refreshToken = loginResponseDto.getRefreshToken();

        Cookie cookie = new Cookie("refresh_token",refreshToken);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(loginResponseDto , HttpStatus.OK);

    }


    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request , HttpServletResponse response){
        String refreshTokenRetrieved = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName())).findFirst().map(cookie -> cookie.getValue()).orElseThrow(()-> new AuthenticationServiceException("refresh token could not be found"));

        LoginResponseDto loginResponseDto = authService.refresh(refreshTokenRetrieved);

        String currentRefreshToken = loginResponseDto.getRefreshToken();

        Cookie cookie = new Cookie("refresh_token",currentRefreshToken);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(loginResponseDto , HttpStatus.OK);
    }

}
