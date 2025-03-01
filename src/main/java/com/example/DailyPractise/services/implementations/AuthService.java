package com.example.DailyPractise.services.implementations;

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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public SignUpDto signUp(@Valid SignUpDto signUpData) {

        String email = signUpData.getEmail();

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new BadCredentialsException("user with email : "+ email+" already exists");
        }

        UserEntity toBeSaved = modelMapper.map(signUpData , UserEntity.class);
        toBeSaved.setPassword(passwordEncoder.encode(toBeSaved.getPassword()));

        UserEntity savedUser = userRepository.save(toBeSaved);
        return modelMapper.map(savedUser , SignUpDto.class);
    }

    public String login(@Valid LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail() , loginDto.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String token = jwtService.generateToken(user);
        return token;

    }
}
