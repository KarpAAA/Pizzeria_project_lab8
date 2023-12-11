package com.example.lab_08_java.services;


import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.RestaurantDTO;
import com.example.lab_08_java.data.generation.DefaultGenerationStrategy;
import com.example.lab_08_java.data.generation.GenerationStrategy;
import com.example.lab_08_java.data.generation.OtherGenerationStrategy;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Setter
public class ScheduledServices {
    private final RestaurantServices restaurantServices;
    private final Restaurant restaurant;
    private final SimpMessagingTemplate messagingTemplate;
    private final DefaultGenerationStrategy defaultGenerationStrategy;
    private final OtherGenerationStrategy otherGenerationStrategy;
    private GenerationStrategy generationStrategy;
    private final KafkaTemplate<String, RestaurantDTO> kafkaTemplate;
    private final KafkaTemplate<String, String> kafkaTemplateEvents;

    public ScheduledServices(RestaurantServices restaurantServices,
                             Restaurant restaurant,
                             SimpMessagingTemplate messagingTemplate,
                             DefaultGenerationStrategy defaultGenerationStrategy,
                             OtherGenerationStrategy otherGenerationStrategy,
                             @Qualifier(value = "defaultGenerationStrategy") GenerationStrategy generationStrategy,
                             KafkaTemplate<String, RestaurantDTO> kafkaTemplate, KafkaTemplate<String, String> kafkaTemplateEvents) {
        this.restaurantServices = restaurantServices;
        this.restaurant = restaurant;
        this.messagingTemplate = messagingTemplate;
        this.generationStrategy = generationStrategy;
        this.defaultGenerationStrategy = defaultGenerationStrategy;
        this.otherGenerationStrategy = otherGenerationStrategy;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateEvents = kafkaTemplateEvents;
    }

    public void changeStrategyToDefault(){
        generationStrategy = defaultGenerationStrategy;
    }

    public void changeStrategyToOther(){
        generationStrategy = otherGenerationStrategy;
    }

    @Scheduled(fixedRateString = "${restaurant.generate.client.delay}")
    public void generateClient() {
        Client c = generationStrategy.generateClient();
        kafkaTemplateEvents.send("events_topic", "Client [" + c.getOrder().getNumber() + "] was created!");
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

//    @Scheduled(fixedRateString = "${sending.restaurantInfo.delay}")
//    public void send() {
//        restaurantServices.reloadRestaurantState();
//        messagingTemplate.convertAndSend("/topic/restaurant", restaurant);
//        kafkaTemplate.send("restaurant_topic2", new RestaurantDTO(restaurant.getPaydesks()));
//    }
}
