package com.example.DailyPractise.config;


import com.example.DailyPractise.basic.Apple;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // this class contains methods which are source for beans
public class AppConfig {

    @Bean
    Apple getApple(){ // this is one way for creating bean .
        return new Apple();
    }
}
