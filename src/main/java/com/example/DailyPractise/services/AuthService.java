package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.UserRepository;
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

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder; // Injecting the PasswordEncoder



    public SignUpDto signUp(@Valid SignUpDto signUpData) {
        String email = signUpData.getEmail();
        Optional<UserEntity> user = userRepository.findUserByEmail(email);
        if(user.isPresent()){
            throw new BadCredentialsException("email : " +email+" is already in use , please sign up with other email");
        }

        UserEntity toBeSavedUser = modelMapper.map(signUpData,UserEntity.class);
        toBeSavedUser.setPassword(passwordEncoder.encode(toBeSavedUser.getPassword())); // encoding the bassword

        UserEntity savedUser = userRepository.save(toBeSavedUser);

        return modelMapper.map(savedUser , SignUpDto.class);
    }

    public String login(@Valid LoginDto loginDto) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // Retrieve authenticated user details
        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();

        // Generate JWT Token
        return jwtService.generateToken(authenticatedUser);
    }
}
