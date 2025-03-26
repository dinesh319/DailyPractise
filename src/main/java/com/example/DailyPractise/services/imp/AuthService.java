package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;


    public SignUpDto signUp(@Valid SignUpDto signUpData) {

        String email = signUpData.getEmail();

        Optional<UserEntity> user = userService.findByEmail(email);
        if(user.isPresent()){
            throw new BadCredentialsException("email : "+email+" is already used , please use other email for signup");
        }

        UserEntity toBeSaved = modelMapper.map(signUpData,UserEntity.class);
        toBeSaved.setPassword(passwordEncoder.encode(toBeSaved.getPassword()));

        UserEntity saved = userService.save(toBeSaved);

        return modelMapper.map(saved,SignUpDto.class);


    }


    public LoginResponseDto login(@Valid LoginDto loginData) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail(),loginData.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // set the session here
        sessionService.createSession(user,refreshToken);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }



    public LoginResponseDto refreshTheToken(String oldRefreshToken) {

        Long userId = jwtService.getUserIdByToken(oldRefreshToken);
        UserEntity user = userService.findById(userId);

        sessionService.validateSession(oldRefreshToken);


        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return new LoginResponseDto(user.getId(),accessToken,refreshToken);

    }
}
