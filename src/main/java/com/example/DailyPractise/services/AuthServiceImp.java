package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.LoginDto;
import com.example.DailyPractise.dtos.LoginResponseDto;
import com.example.DailyPractise.dtos.SignUpDto;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.UserRepository;
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
public class AuthServiceImp implements AuthService{

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public SignUpDto signUp(SignUpDto signUpData) {

        String email = signUpData.getEmail();
        /*
            check whether email has already been registered or not
         */
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new BadCredentialsException("email : "+email+" has already been used");
        }


        UserEntity toBeSaved = modelMapper.map(signUpData , UserEntity.class);
        /*
                encode the password before saving it in database
         */
        toBeSaved.setPassword(passwordEncoder.encode(toBeSaved.getPassword()));

        UserEntity savedUser = userRepository.save(toBeSaved);
        return modelMapper.map(savedUser,SignUpDto.class);

    }

    @Override
    public LoginResponseDto login(LoginDto loginData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getEmail() , loginData.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponseDto(user.getId(),accessToken,refreshToken);


    }

    @Override
    public LoginResponseDto refreshTheToken(String refreshToken) {

        Long userId = jwtService.getUserIdByToken(refreshToken);

        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new BadCredentialsException("user with id : "+userId+" does not exists"));

        String accessToken = jwtService.generateAccessToken(user);

        String refreshTokenNewOne = jwtService.generateRefreshToken(user);

        return new LoginResponseDto(user.getId(),accessToken,refreshTokenNewOne);

    }
}
