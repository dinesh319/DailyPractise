package com.example.DailyPractise.db;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "db.env" , havingValue = "production")
@Component
public class ProdDB implements DBInterface{
    @Override
    public void getDbData() {
        System.out.println("production data is achieved");
    }
}
