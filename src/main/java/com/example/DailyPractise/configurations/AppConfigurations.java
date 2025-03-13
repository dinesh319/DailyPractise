package com.example.DailyPractise.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigurations {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
