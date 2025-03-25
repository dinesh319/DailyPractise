package com.example.DailyPractise.services.implementations;

import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public SignUpDto signUp(@Valid SignUpDto signUpData) {

        String email = signUpData.getEmail();
        Optional<UserEntity> user = userService.findUserByEmail(email);

        if(user.isPresent()){
            throw new BadCredentialsException("user with email : "+email+" already exists");
        }

        UserEntity userToBeSaved = modelMapper.map(signUpData , UserEntity.class);
        userToBeSaved.setPassword(passwordEncoder.encode(userToBeSaved.getPassword()));

       UserEntity savedUser =  userService.saveUser(userToBeSaved);

        return modelMapper.map(savedUser , SignUpDto.class);

    }

    public LoginResponseDto login(@Valid LoginDto loginData) {

        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail() , loginData.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDto(user.getId(), accessToken,refreshToken);
    }

    public LoginResponseDto refresh(String refreshTokenRetrieved) {

        Long userId = jwtService.getUserIdFromToken(refreshTokenRetrieved);

        UserEntity user = userService.findUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }
}
