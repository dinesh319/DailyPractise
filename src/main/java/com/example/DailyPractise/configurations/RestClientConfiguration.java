package com.example.DailyPractise.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Value("${base.url.users}")
    private String baseUrl;

    @Bean
    public RestClient getRestClient(){
        return RestClient.builder().baseUrl(baseUrl).build();
    }

}
