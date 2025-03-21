package com.example.DailyPractise.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AudtiAwareImp implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("dinesh hanumanthu");
    }
}
