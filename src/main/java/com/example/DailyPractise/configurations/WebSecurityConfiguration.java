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
public class WebSecurityConfiguration {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /*
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).formLogin(Customizer.withDefaults());
        return httpSecurity.build();

        httpSecurity
                .authorizeHttpRequests(auth->auth.requestMatchers("/posts/**")
                        .permitAll().anyRequest().authenticated()).formLogin(Customizer.withDefaults());
      return   httpSecurity.build();

        */

        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/posts/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()).formLogin(Customizer.withDefaults());

         return httpSecurity.build();

    }


    @Bean
    UserDetailsService userDetailsService(){
        UserDetails normalUser = User.withUsername("dino")
                .password(passwordEncoder().encode("dino")).roles("USER").build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(normalUser,admin);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
