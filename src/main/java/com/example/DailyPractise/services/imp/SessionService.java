package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.entities.SessionEntity;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private int SESSION_LIMIT = 2;
    private final SessionRepository sessionRepository;

    public void createSession(UserEntity user, String refreshToken) {

        List<SessionEntity> sessionEntities = sessionRepository.findByUser(user);

        if(sessionEntities.size() == SESSION_LIMIT){
            sessionEntities.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity lastSession = sessionEntities.getFirst();
            sessionRepository.delete(lastSession);
        }

        SessionEntity newSession = SessionEntity.builder()
                .refreshToken(refreshToken).user(user).lastUsedAt(LocalDateTime.now())
                .build();

        sessionRepository.save(newSession);

    }

    public void validateSession(UserEntity user, String oldRefreshToken) {

        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(oldRefreshToken);
        if (sessionEntity != null){
            sessionEntity.setLastUsedAt(LocalDateTime.now());
            sessionRepository.save(sessionEntity);
        }
    }
}
