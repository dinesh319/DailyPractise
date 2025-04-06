package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.SessionEntity;
import com.example.DailyPractise.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity , Long> {

    List<SessionEntity> findByUser(UserEntity user);

    SessionEntity findByRefreshToken(String oldRefreshToken);
}
