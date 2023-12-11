package com.example.lab_08_java.aspects;

import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.RestaurantDTO;
import com.example.lab_08_java.services.RestaurantServices;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class OrderAspect {
    private final Restaurant restaurant;
    private final RestaurantServices restaurantServices;
    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaTemplate<String, RestaurantDTO> kafkaTemplate;


    @Pointcut("execution(* com.example.lab_08_java.controllers..*(..))")
    public void allMethods() {
    }


    @Pointcut("execution(* com.example.lab_08_java.services.ScheduledServices.*(..))")
    public void scheduledServicesAllMethods() {
    }



    @After("allMethods() || scheduledServicesAllMethods()")
    public void afterGetterExecution() {
        restaurantServices.reloadRestaurantState();
        messagingTemplate.convertAndSend("/topic/restaurant", restaurant);
        kafkaTemplate.send("restaurant_topic2", new RestaurantDTO(restaurant.getPaydesks()));

    }

}
