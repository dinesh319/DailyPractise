package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static com.example.DailyPractise.entities.enums.Roles.ADMIN;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setUser(){
        user = UserEntity.builder()
                .email("dq@gmail.com").password("dq").roles(Set.of(ADMIN))
                .build();
    }

    @Test
    @DisplayName("proper email")
    void testFindByEmail_WhenEmailIsPresent_ThenReturnEmployee(){

        userRepository.save(user);

        String email = "dq@gmail.com";

        Optional<UserEntity> user1 = userRepository.findByEmail(email);

        Assertions.assertThat(user1)
                .isPresent()
                .hasValueSatisfying(user ->
                        Assertions.assertThat(user.getEmail().equals(email))
                        );
    }

    @Test
    @DisplayName("wrong email")
    void testFindByEmail_WhenEmailIsAbsent_ThenReturnNull(){

        String email = "dineshhanumanthu23232@gmail.com";

        Optional<UserEntity> user = userRepository.findByEmail(email);

        Assertions.assertThat(user).isNotPresent();

    }

}