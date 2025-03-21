package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service (for using in memory thing not actual database)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user with email : "+username+" does not exists"));
    }
}
