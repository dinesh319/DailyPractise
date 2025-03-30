package com.example.DailyPractise.configurations;

import com.example.DailyPractise.auth.CustomSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.DailyPractise.entities.enums.Permissions.*;
import static com.example.DailyPractise.entities.enums.Roles.ADMIN;
import static com.example.DailyPractise.entities.enums.Roles.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final CustomSecurityFilter customSecurityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // added security at method level please check post controllers
         httpSecurity
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.POST , "/posts/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(HttpMethod.GET,"/post/**").hasAnyRole(USER.name(), ADMIN.name())
//                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(HttpMethod.GET,"/posts/**").hasAnyAuthority(POST_VIEW.name())
//                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAnyAuthority(POST_DELETE.name())
//                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyAuthority(POST_CREATE.name())
//                        .requestMatchers(HttpMethod.PATCH,"/posts/**").hasAnyAuthority(POST_UPDATE.name())

                        .anyRequest().authenticated())
                 .addFilterBefore(customSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                 .csrf(csrfConfig -> csrfConfig.disable())
                 .sessionManagement(sessMangConfig -> sessMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

         return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
