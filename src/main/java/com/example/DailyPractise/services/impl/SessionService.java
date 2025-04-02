package com.example.DailyPractise.services.impl;

import com.example.DailyPractise.entities.SessionEntity;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private int SESSION_LIMIT = 2;
    private final SessionRepository sessionRepository;

    public void createSession(UserEntity user, String refreshToken) {

        List<SessionEntity> sessionEntities = sessionRepository.findAll();

        if(sessionEntities.size() == SESSION_LIMIT){
            sessionEntities.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity lastUsed = sessionEntities.getFirst();
            sessionRepository.delete(lastUsed);
        }

        SessionEntity newSession = new SessionEntity();
        newSession.setLastUsedAt(LocalDateTime.now());
        newSession.setUser(user);
        newSession.setRefreshToken(refreshToken);

        sessionRepository.save(newSession);
    }

    public void validateSession(String oldRefreshToken) {

        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(oldRefreshToken);
        if(sessionEntity != null){
            sessionEntity.setLastUsedAt(LocalDateTime.now());
            sessionRepository.save(sessionEntity);
        }
    }
}
