package com.example.lab_08_java.services;


import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.generation.DefaultGenerationStrategy;
import com.example.lab_08_java.data.generation.GenerationStrategy;
import com.example.lab_08_java.data.generation.OtherGenerationStrategy;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
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

    public ScheduledServices(RestaurantServices restaurantServices,
                             Restaurant restaurant,
                             SimpMessagingTemplate messagingTemplate,
                             DefaultGenerationStrategy defaultGenerationStrategy,
                             OtherGenerationStrategy otherGenerationStrategy,
                             @Qualifier(value = "defaultGenerationStrategy") GenerationStrategy generationStrategy) {
        this.restaurantServices = restaurantServices;
        this.restaurant = restaurant;
        this.messagingTemplate = messagingTemplate;
        this.generationStrategy = generationStrategy;
        this.defaultGenerationStrategy = defaultGenerationStrategy;
        this.otherGenerationStrategy = otherGenerationStrategy;
    }

    public void changeStrategyToDefault(){
        generationStrategy = defaultGenerationStrategy;
    }

    public void changeStrategyToOther(){
        generationStrategy = otherGenerationStrategy;
    }

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
