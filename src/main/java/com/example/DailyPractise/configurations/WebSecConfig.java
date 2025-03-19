package com.example.DailyPractise.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecConfig {
/*
.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        This ensures all incoming HTTP requests require authentication. No endpoint is publicly accessible unless explicitly allowed.

 .formLogin(Customizer.withDefaults())
enables Spring Security's default login form. Users will be redirected to a login page if they try to access a protected resource.
Spring will handle authentication using UserDetailsService (which you defined in UserService). After login, users will be redirected to a default page (usually /).
 */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
//
//            return httpSecurity.build();

        httpSecurity
                .authorizeHttpRequests(auth -> auth.requestMatchers("/posts").authenticated())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

// creating users for in-memory with respect to roles
    @Bean
    UserDetailsService myInMemoryUserDetailService(){
        UserDetails normalUser = User.withUsername("dinesh")
                .password(passwordEncoder().encode("dinesh"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(normalUser,admin);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
