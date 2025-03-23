package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.LogInDto;
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

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final  JwtService jwtService;


    public SignUpDto signUp(@Valid SignUpDto signUpData) {

        String email = signUpData.getEmail();

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw  new BadCredentialsException("user with email : "+ email +" already exists .");
        }

        UserEntity toBeCreated = modelMapper.map(signUpData , UserEntity.class);
        toBeCreated.setPassword(passwordEncoder.encode(toBeCreated.getPassword()));

        UserEntity saved = userRepository.save(toBeCreated);

        return modelMapper.map(saved,SignUpDto.class);

    }

    public String login(@Valid LogInDto logInData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInData.getEmail(),logInData.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return jwtService.generateToken(user);

    }
}
