package com.example.DailyPractise.service.imp;

import com.example.DailyPractise.service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class DataServiceImpProd implements DataService {
    @Override
    public void getData() {
            System.out.println("this is production environment");
    }
}
