package com.example.lab_08_java.aspects;

import com.example.lab_08_java.data.Client;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class OrderAspect {

    @AfterReturning(pointcut = "execution(* com.example.lab_08_java.services.ClientServices.createNewClient(..))"
            , returning = "client")
    public void afterClientCreation(Client client) {
        client.getOrder().setCreatedOrderTime(LocalTime.now());
    }

    @AfterReturning(pointcut = "execution(* com.example.lab_08_java.services.ClientServices.createNewClient(..))"
            , returning = "client")
    public void afterClientCreation1(Client client) {
        client.getOrder().setCreatedOrderTime(LocalTime.now());
    }
}
