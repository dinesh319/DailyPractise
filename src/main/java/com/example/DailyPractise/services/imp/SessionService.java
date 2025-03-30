package com.example.DailyPractise.services.imp;

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

        List<SessionEntity> sessions = sessionRepository.findByUser(user);

        if(sessions.size() == SESSION_LIMIT){
            sessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity lastUsed = sessions.getFirst();
            sessionRepository.delete(lastUsed);
        }

        SessionEntity newSession = SessionEntity.builder()
                .refreshToken(refreshToken).user(user).lastUsedAt(LocalDateTime.now())
                .build();

        sessionRepository.save(newSession);


    }

    public void validate(String oldRefreshToken) {

        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(oldRefreshToken);
        sessionEntity.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(sessionEntity);
    }
}
