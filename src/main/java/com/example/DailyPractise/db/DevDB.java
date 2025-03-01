package com.example.DailyPractise.db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "db.env" , havingValue = "development")
@Component
public class DevDB implements DBInterface{


    @Override
    public void getDbData() {
        System.out.println("developement data is achieved");
    }
}
