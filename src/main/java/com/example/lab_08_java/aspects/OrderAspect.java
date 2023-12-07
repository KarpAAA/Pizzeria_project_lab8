package com.example.lab_08_java.aspects;

import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.services.RestaurantServices;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderAspect {
    private final Restaurant restaurant;
    private final RestaurantServices restaurantServices;
    private final SimpMessagingTemplate messagingTemplate;

    @AfterReturning(pointcut = "execution(* com.example.lab_08_java.services.ClientServices.createNewClient(..))"
            , returning = "client")
    public void afterClientCreation(Client client) {
        client.getOrder().setCreatedOrderTime(LocalTime.now());
    }


//    @After("execution(* com.example.lab_08_java.data.Restaurant.get*())")
//    public void afterGetterExecution() {
//        restaurantServices.reloadRestaurantState();
//        messagingTemplate.convertAndSend("/topic/restaurant", restaurant);
//    }

}
