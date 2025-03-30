package com.example.DailyPractise.services.imp;

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
    private final SessionService sessionService;

    public SignUpDto signup(@Valid SignUpDto signupData) {
        String email = signupData.getEmail();

        Optional<UserEntity> user = userService.findByEmail(email);
        if (user.isPresent()){
            throw  new BadCredentialsException("email "+email+" has already been used please use other email");
        }

        UserEntity toBeSaved = modelMapper.map(signupData,UserEntity.class);
        toBeSaved.setPassword(passwordEncoder.encode(toBeSaved.getPassword()));

        UserEntity savedUser = userService.save(toBeSaved);

        return modelMapper.map(savedUser, SignUpDto.class);
    }

    public LoginResponseDto login(@Valid LoginDto loginData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail(),loginData.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.createSession(user,refreshToken);


        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }


    public LoginResponseDto refresh(String oldRefreshToken) {
        Long userId = jwtService.getIdByToken(oldRefreshToken);
        UserEntity user = userService.findById(userId);

        sessionService.validate(oldRefreshToken);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }
}
