package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.entities.SessionEntity;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final int SESSION_LIMIT = 2;


    public void createSession(UserEntity user, String refreshToken) {

        List<SessionEntity> sessionEntities = sessionRepository.findByUser(user);

        if(sessionEntities.size() == SESSION_LIMIT){
             sessionEntities.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
             SessionEntity oldSession = sessionEntities.getFirst();
             sessionRepository.delete(oldSession);
        }

        SessionEntity newSession = SessionEntity.builder()
                .refreshToken(refreshToken).user(user)
                .build();

        sessionRepository.save(newSession);

    }


    public void validateSession(String refreshToken) {

        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("no session with the refresh token"));

        sessionEntity.setLastUsedAt(LocalDateTime.now());

        sessionRepository.save(sessionEntity);
    }
}
