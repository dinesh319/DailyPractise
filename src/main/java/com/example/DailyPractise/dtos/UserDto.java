package com.example.DailyPractise.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private String email;

    private String firstname;

    private String lastName;

    private String avatar;
}
