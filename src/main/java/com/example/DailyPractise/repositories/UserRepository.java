package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity , Long> {
    Optional<UserEntity> findByEmail(String username);
}
