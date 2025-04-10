package com.example.DailyPractise.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
@Slf4j
public class LoggingAspect {

//    @Before("execution(* com.example.DailyPractise.services.imp.DemoService.*(..))")
    @Before("forAllMethodsOfServicePointCut()")
    public void logBeforeAllMethods(){
        log.info("logging before methods");
    }

//    @After("within(com.example.DailyPractise.services.imp.DemoService)")
    public void logAfterAllMethods(){
        log.info("logging after methods");
    }

//    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void logForAnnotation(){
        log.info("annotation log");
    }



    @Pointcut("execution(* com.example.DailyPractise.services.imp.DemoService.*(..))")
    public void forAllMethodsOfServicePointCut(){

    }

}
