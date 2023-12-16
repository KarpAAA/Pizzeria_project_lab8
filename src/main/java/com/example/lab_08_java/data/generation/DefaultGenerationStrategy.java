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
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, String> kafkaTemplateEvents;
    private final TaskScheduler taskScheduler;
    private final Restaurant restaurant;

    /**
     * Creates a new client with a random order. The method calculates the time interval between the order creation and the
     * next available paydesk by summing up the creation times of all pizzas in the order. Then, it schedules a task to
     * cancel the order after the calculated time interval.
     * @return the new client
     */
    @Override
public Client generateClient() {

    Client c = clientServices.createNewClient(clientServices.randomOrder());
    Long plusMills = c.getOrder()
            .getPizzaList().stream().map(PizzaDTO::getCreationTime)
            .reduce(0L, Long::sum);

    taskScheduler.schedule(new Runnable() {
        @Override
        public void run() {
            cancelOrderMethod(c);
        }
    }, Instant.now().plus(plusMills, ChronoUnit.MILLIS));


    Paydesk paydesk = paydeskServices.findBestPaydesk(restaurant);
    QueueRequest queueRequest = new QueueRequest(
            restaurant.getPaydesks().indexOf(paydesk),
            restaurant.getClients().indexOf(c)
    );
    paydeskServices.standToQueue(queueRequest, restaurant);

    return c;
}

    public void cancelOrderMethod(Client c) {
        if (c.getOrder().isCompleted()) return;
        restaurant.getClients().remove(c);
        restaurant.getCurrentOrders().removeIf(o -> o.getNumber() == c.getOrder().getNumber());
        Paydesk paydesk = restaurant.getPaydesks().stream().filter(p -> p.getClients().contains(c)).findFirst().get();
        paydesk.getClients().remove(c);
        restaurant.getStat().getFailedOrders().add(c.getOrder());
        kafkaTemplateEvents.send("events_failed_topic", "Order [" + c.getOrder().getNumber() + "] was failed!");

    }

}
