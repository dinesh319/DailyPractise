package com.example.DailyPractise.controllers;


import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.services.imp.AuthService;
import com.example.DailyPractise.services.imp.JwtService;
import com.example.DailyPractise.services.imp.UserService;
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
    public ResponseEntity<SignUpDto> signup(@RequestBody @Valid SignUpDto signupData){
        SignUpDto signedData = authService.signup(signupData);
        return new ResponseEntity<>(signedData, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginData,
                                                  HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(loginData);
        String refreshToken = loginResponseDto.getRefreshToken();

        Cookie cookie = new Cookie("refresh_token",refreshToken);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
    }


    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletResponse response , HttpServletRequest request){

       String oldRefreshToken =  Arrays.stream(request.getCookies())
               .filter(cookie -> "refresh_token".equals(cookie.getName()))
               .findFirst().map(cookie -> cookie.getValue()).orElseThrow(()-> new  AuthenticationServiceException("refresh token is not available as cookie , please login"));


       LoginResponseDto loginResponseDto = authService.refresh(oldRefreshToken);
       String refreshToken = loginResponseDto.getRefreshToken();


        Cookie cookie = new Cookie("refresh_token",refreshToken);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);





    }
}
