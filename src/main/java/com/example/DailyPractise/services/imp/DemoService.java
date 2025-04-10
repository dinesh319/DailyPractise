package com.example.DailyPractise.services.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    public void nameMethodCalling(){
        System.out.println("dino");
    }


    public void contactMethodCalling(){
        System.out.println("9014101984");
    }

    @Transactional
    public void someRandomCheck(){
        System.out.println("random check");
    }

    public void employeeMethod(Long employeeId){
        System.out.println("employee id is "+employeeId);
    }


}
