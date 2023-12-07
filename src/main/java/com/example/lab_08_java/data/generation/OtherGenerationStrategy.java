package com.example.lab_08_java.data.generation;

import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.models.other.QueueRequest;
import com.example.lab_08_java.services.ClientServices;
import com.example.lab_08_java.services.PaydeskServices;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
@Service
public class OtherGenerationStrategy implements GenerationStrategy {
    private final PaydeskServices paydeskServices;
    private final ClientServices clientServices;
    private final TaskScheduler taskScheduler;
    private final Restaurant restaurant;


    @Override
    public void generateClient() {
        Client c = clientServices.createNewClient(clientServices.randomOrderWithGift());
        Long plusMills  = c.getOrder()
                .getPizzaList().stream().map(PizzaDTO::getCreationTime)
                .reduce(0L,Long::sum);

        LocalDateTime localDateTime =
                LocalDateTime.now()
                        .atZone(ZoneId.of("Europe/Kiev"))
                        .plus(plusMills, ChronoUnit.MILLIS)
                        .toLocalDateTime();

        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                if (c.getOrder().isCompleted()) return;
                restaurant.getClients().remove(c);
                restaurant.getCurrentOrders().removeIf(o -> o.getNumber() == c.getOrder().getNumber());
                Paydesk paydesk = restaurant.getPaydesks().stream().filter(p -> p.getClients().contains(c)).findFirst().get();
                paydesk.getClients().remove(c);
                restaurant.getStat().getFailedOrders().add(c.getOrder());
                System.out.println("Order[" + c.getOrder().getNumber() + "] was failed to completed!!!");
            }
        }, localDateTime.toInstant(ZoneOffset.ofHours(0)));

        Paydesk paydesk = paydeskServices.findBestPaydesk(restaurant);
        QueueRequest queueRequest = new QueueRequest(
                restaurant.getPaydesks().indexOf(paydesk),
                restaurant.getClients().indexOf(c)
        );
        paydeskServices.standToQueue(queueRequest, restaurant);
    }


}
