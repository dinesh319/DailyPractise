package com.example.DailyPractise.configurations;

import com.example.DailyPractise.auth.CustomizedSecurityFilterChain;
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


    private final CustomizedSecurityFilterChain customizedSecurityFilterChain;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity

                 .csrf(csrfConfig -> csrfConfig.disable())
                 .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .addFilterBefore(customizedSecurityFilterChain , UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth  -> auth.requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated());


         return httpSecurity.build();


    }
}
