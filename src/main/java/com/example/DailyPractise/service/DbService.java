package com.example.DailyPractise.service;

import com.example.DailyPractise.db.DBInterface;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    private final DBInterface db;

    public DbService(DBInterface db) {  // one way of dependency injection
        this.db = db;
    }

    public void getData(){
        db.getDbData();
    }
}
