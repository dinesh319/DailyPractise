package com.example.DailyPractise.configurations;

import com.example.DailyPractise.auth.CustomizeFilteredSecurityChain;
import com.example.DailyPractise.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomizeFilteredSecurityChain customizeFilteredSecurityChain;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**", "/error",
                                "/login**",
                                "/login/oauth2/**",
                                "/oauth2/**").permitAll()
                        .anyRequest().authenticated())

                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Maintain session
                )
                .addFilterBefore(customizeFilteredSecurityChain, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                                .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );

        return httpSecurity.build();
    }



}
