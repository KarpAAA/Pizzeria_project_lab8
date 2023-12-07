package com.example.lab_08_java.services;


import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.data.generation.GenerationStrategy;
import com.example.lab_08_java.models.other.QueueRequest;
import com.example.lab_08_java.sockets.RestaurantSocket;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.query.sqm.TemporalUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;


@Service
@RequiredArgsConstructor
@Setter
public class ScheduledServices {
    private final RestaurantServices restaurantServices;
    private final Restaurant restaurant;
    private final SimpMessagingTemplate messagingTemplate;

    @Qualifier("defaultGenerationStrategy")
    private final GenerationStrategy generationStrategy;

    @Scheduled(fixedRateString = "${restaurant.generate.client.delay}")
    public void generateClient() {
        generationStrategy.generateClient();
    }

    @Scheduled(fixedRateString = "${restaurant.cook.work.delay}")
    public void cooksMakeSteps() {

        restaurant.getCooks().forEach(c -> {
                    if (c.isIfWorking()) {
                        restaurantServices
                                .orderPizzaMakeStep(restaurantServices.findStepToComplete(c));
                    }
                }
        );
    }
    @Scheduled(fixedRateString = "${sending.restaurantInfo.delay}")
    public void send() {
        restaurantServices.reloadRestaurantState();
        messagingTemplate.convertAndSend("/topic/restaurant", restaurant);
    }
}
