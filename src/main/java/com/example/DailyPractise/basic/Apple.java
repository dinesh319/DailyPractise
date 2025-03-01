package com.example.DailyPractise.basic;


import org.springframework.stereotype.Component;

@Component   // Apple has been a bean beacuse of @Component
public class Apple {


    public void eat(){
        System.out.println(" i am eating apple");
    }

}
