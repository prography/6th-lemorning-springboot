package com.lemorning.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodTraceAop {

    @Around("execution(* com.lemorning..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        }finally {
            System.out.println("END: " + joinPoint.toString());
        }
    }
}