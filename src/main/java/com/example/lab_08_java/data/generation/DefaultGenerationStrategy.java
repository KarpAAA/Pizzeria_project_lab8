package com.example.lab_08_java.data.generation;

import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.models.other.QueueRequest;
import com.example.lab_08_java.services.ClientServices;
import com.example.lab_08_java.services.PaydeskServices;
import com.example.lab_08_java.sockets.RestaurantSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;



@Service
@RequiredArgsConstructor
public class DefaultGenerationStrategy implements GenerationStrategy {
    private final PaydeskServices paydeskServices;
    private final ClientServices clientServices;
    private final TaskScheduler taskScheduler;
    private final Restaurant restaurant;


    @Override
    public void generateClient() {
        Client c = clientServices.createNewClient(clientServices.randomOrder());
        Long plusMills  = c.getOrder()
                .getPizzaList().stream().map(PizzaDTO::getCreationTime)
                .reduce(0L,Long::sum);

        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                if (c.getOrder().isCompleted()) return;
                restaurant.getClients().remove(c);
                restaurant.getCurrentOrders().removeIf(o -> o.getNumber() == c.getOrder().getNumber());
                Paydesk paydesk = restaurant.getPaydesks().stream().filter(p -> p.getClients().contains(c)).findFirst().get();
                paydesk.getClients().remove(c);
                restaurant.getStat().getFailedOrders().add(c.getOrder());
            }
        }, Instant.now().plus(plusMills, ChronoUnit.MILLIS));
        Paydesk paydesk = paydeskServices.findBestPaydesk(restaurant);
        QueueRequest queueRequest = new QueueRequest(
                restaurant.getPaydesks().indexOf(paydesk),
                restaurant.getClients().indexOf(c)
        );
        paydeskServices.standToQueue(queueRequest, restaurant);
    }


}
