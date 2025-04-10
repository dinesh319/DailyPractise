package com.example.DailyPractise.services.imp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoServiceTest {


    @Autowired
    private DemoService demoService;

    @Test
    public void nameMethodCalling(){
        demoService.nameMethodCalling();
    }

    @Test
    public void contactMethodCalling(){
        demoService.contactMethodCalling();
    }

    @Test
    public void annotationMethodCalling(){
        demoService.someRandomCheck();
    }

    @Test
    public void employeeMethodCalling(){
        demoService.employeeMethod(0L);
    }
}