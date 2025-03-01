package com.example.DailyPractise.basic;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

//@Component   // Apple has been a bean because of @Component
public class Apple {

    @PostConstruct
    public void callBeforeAppleIsUsed(){  // invoked immediately after a bean is constructed and all of
        // its dependencies are injected
        System.out.println("wash the apples before eating");
    }


    public void eat(){
        System.out.println(" i am eating apple");
    }

    @PreDestroy
    public void beforeBeanDestroying(){ // invoked just before the bean is destroyed by the container
        System.out.println("finished about the apple");
    }



}
