package com.example.DailyPractise.clients;

import com.example.DailyPractise.dtos.UserDto;

import java.util.List;


public interface UserService {

    public List<?> getAllUsers();

    public UserDto getUserById(Long userId);

    public void createUser(UserDto user);

    public void deleteUser(Long userId);

}
