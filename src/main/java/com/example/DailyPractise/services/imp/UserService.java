package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(()-> new BadCredentialsException("user with email "+username+" does not exists"));
        return user;
    }

    public UserEntity findById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new BadCredentialsException("user with userid : "+userId+" does not exists"));
        return user;
    }

    public Optional<UserEntity> findByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user;
    }

    public UserEntity save(UserEntity toBeSavedUser) {
        UserEntity user = userRepository.save(toBeSavedUser);
        return user;
    }
}
