package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity , Long> {

    SessionEntity findByRefreshToken(String oldRefreshToken);

}
