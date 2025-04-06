package com.example.DailyPractise.dtos;

import com.example.DailyPractise.entities.enums.Permissions;
import com.example.DailyPractise.entities.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String email;

    private String password;

    private Set<Roles> roles;

    private Set<Permissions> permissions;
}
