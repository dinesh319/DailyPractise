package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.LogInDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<SignUpDto> signUpUser(@RequestBody @Valid SignUpDto signUpData){
        SignUpDto signedUser = authService.signUp(signUpData);
        return new ResponseEntity<>(signedUser , HttpStatus.OK);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse<?>> logInUser(@RequestBody @Valid LogInDto logInData , HttpServletResponse response){
        String token = authService.login(logInData);

        Cookie cookie =  new Cookie("token", token);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(new ApiResponse<String>(token),HttpStatus.OK);
    }
}
