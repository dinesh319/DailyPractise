package com.example.DailyPractise.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect1 {

    /*

    @Around("logForEmployeeMethod()")
    public void validatingId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object args[] = proceedingJoinPoint.getArgs();
        Long employeeId = (Long) args[0];

        if (employeeId > 0){
            proceedingJoinPoint.proceed();
        }else{
            log.info("this is invalid id "+employeeId);

        }


    }

    @Pointcut("execution(* com.example.DailyPractise.services.imp.DemoService.employeeMethod(..))")
    public void logForEmployeeMethod(){

    }

    */



    @Pointcut("execution(* com.example.DailyPractise.services.imp.DemoService.*(..))")
    public void logForAllMethods(){
        
    }

}
