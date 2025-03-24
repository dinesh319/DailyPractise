package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.services.AuthService;
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
        SignUpDto savedUser = authService.signUp(signUpData);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginData,
                                                  HttpServletResponse response){

        LoginResponseDto loginResponseDto = authService.login(loginData);

        Cookie cookie = new Cookie("refresh_token", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(loginResponseDto ,HttpStatus.OK);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh_token".equals(cookie.getName()) )
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(()-> new AuthenticationServiceException(" refresh token is not found in cookies"));
        LoginResponseDto loginResponseDto = authService.refreshTheToken(refreshToken);
        return new ResponseEntity<>(loginResponseDto , HttpStatus.OK);
    }
}
