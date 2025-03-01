package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.services.implementations.AuthService;
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
    public ResponseEntity<SignUpDto> signUp(@RequestBody @Valid SignUpDto signUpData){
        SignUpDto savedData = authService.signUp(signUpData);
        return new ResponseEntity<>(savedData , HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse<String >> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response){
        String token = authService.login(loginDto);

        Cookie cookie = new Cookie("token" , token);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(new ApiResponse<>(token) , HttpStatus.OK);
    }

}
