package com.example.DailyPractise.services.impl;

import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;


    public SignUpDto signup(@Valid SignUpDto signUpData) {
        String email = signUpData.getEmail();

        Optional<UserEntity> user = userService.findByEmail(email);
        if (user.isPresent()){
            throw new AuthenticationServiceException("this email"+email+" has already been used please try with other email");
        }

        UserEntity toBeSavedUser = modelMapper.map(signUpData, UserEntity.class);
        toBeSavedUser.setPassword(passwordEncoder.encode(toBeSavedUser.getPassword()));

        UserEntity savedUser = userService.save(toBeSavedUser);

        return modelMapper.map(savedUser, SignUpDto.class);
    }

    public LoginResponseDto login(@Valid LoginDto loginData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail(),loginData.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken =  jwtService.generateRefreshToken(user);

        sessionService.createSession(user,refreshToken);

        return LoginResponseDto.builder()
                .userId(user.getId()).accessToken(accessToken).refreshToken(refreshToken)
                .build();

    }

    public LoginResponseDto refresh(String oldRefreshToken) {

        Long userId = jwtService.getUserIdFromToken(oldRefreshToken);
        sessionService.validateSession(oldRefreshToken);

        UserEntity user = userService.findById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponseDto.builder()
                .userId(userId).accessToken(accessToken).refreshToken(refreshToken)
                .build();

    }
}
