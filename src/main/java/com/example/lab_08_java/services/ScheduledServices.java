package com.example.lab_08_java.services;


import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.models.other.QueueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ScheduledServices {
    private final ClientServices clientServices;
    private final RestaurantServices restaurantServices;
    private final PaydeskServices paydeskServices;
    private final Restaurant restaurant;

    @Scheduled(fixedRateString = "${restaurant.generate.client.delay}")
    public void generateClient() {
        Client c = clientServices.createNewClient();
        Paydesk paydesk = paydeskServices.findBestPaydesk(restaurant);
        QueueRequest queueRequest = new QueueRequest(
                restaurant.getPaydesks().indexOf(paydesk),
                restaurant.getClients().indexOf(c)
        );
        paydeskServices.standToQueue(queueRequest, restaurant);
    }

    @Scheduled(fixedRateString = "5000")
    public void cooksMakeSteps() {
        restaurant.getCooks().forEach(c -> {
                    if (c.isIfWorking()) {
                        restaurantServices
                                .orderPizzaMakeStep(restaurantServices.findStepToComplete(c));
                    }
                }
        );
    }
}
