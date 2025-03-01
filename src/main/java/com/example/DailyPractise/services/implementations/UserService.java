package com.example.DailyPractise.services.implementations;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity  user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user with email : "+username+" does not exists"));
        return user;
    }

    public UserEntity findUserById(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user with id : "+userId+" does not exists"));
        return user;
    }
}
