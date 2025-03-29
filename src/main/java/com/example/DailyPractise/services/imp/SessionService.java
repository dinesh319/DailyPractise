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
    private int SESSION_LIMIT = 2;

    public void createSession(UserEntity user, String refreshToken) {

        List<SessionEntity> sessionEntities = sessionRepository.findByUser(user);

        if(sessionEntities.size() == SESSION_LIMIT){
             sessionEntities.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
             SessionEntity lastUsed = sessionEntities.getFirst();
             sessionRepository.delete(lastUsed);
        }

        SessionEntity sessionEntity = SessionEntity.builder()
                .refreshToken(refreshToken).user(user).lastUsedAt(LocalDateTime.now())
                .build();

        sessionRepository.save(sessionEntity);

    }

    public void validateSession(String refreshToken) {

        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new SessionAuthenticationException("no session found with current refresh token please login"));
            sessionEntity.setLastUsedAt(LocalDateTime.now());
            sessionRepository.save(sessionEntity);



    }
}
