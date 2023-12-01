package com.example.lab_08_java.services;

import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.models.other.QueueRequest;
import com.example.lab_08_java.models.paydesks.DeletePaydeskRequest;
import com.example.lab_08_java.models.paydesks.UpdatePaydeskRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaydeskServices {


    public boolean updatePaydesk(UpdatePaydeskRequest updatePaydeskRequest, Restaurant restaurant) {
        restaurant
                .getPaydesks()
                .get(updatePaydeskRequest.getPaydeskIndex())
                .setAvailability(updatePaydeskRequest.getAvailability());
        return true;
    }

    public void standToQueue(QueueRequest queueRequest, Restaurant restaurant) {
        if(!restaurant.getClients().get(queueRequest.getClientIndex()).getOrder().isCompleted()){
            restaurant.getClients().get(queueRequest.getClientIndex()).setChosenPaydesk(queueRequest.getPaydeskIndex());
            restaurant
                    .getPaydesks()
                    .get(queueRequest.getPaydeskIndex())
                    .getClients()
                    .add(restaurant.getClients().get(queueRequest.getClientIndex()));
        }
    }

    public void leaveFromQueue(QueueRequest queueRequest, Restaurant restaurant) {
        restaurant.getClients().get(queueRequest.getClientIndex()).setChosenPaydesk(-1);
        restaurant
                .getPaydesks()
                .get(queueRequest.getPaydeskIndex())
                .getClients()
                .remove(restaurant.getClients().get(queueRequest.getClientIndex()));
    }

    public Paydesk findBestPaydesk(Restaurant restaurant){
        Map<Paydesk, Long> creationTimes = restaurant
                .getPaydesks()
                .stream()
                .collect(Collectors.toMap(
                        paydesk -> paydesk,
                        paydesk -> paydesk.getClients()
                                .stream()
                                .flatMap(cl -> cl.getOrder()
                                        .getPizzaList()
                                        .stream()
                                        .map(PizzaDTO::getCreationTime)
                                )
                                .reduce(0L, Long::sum)
                ));

        Paydesk best = null;
        long min = Long.MAX_VALUE;

        for (Paydesk p : creationTimes.keySet()) {
            long creationTime = creationTimes.get(p);

            if (creationTime < min) {
                min = creationTime;
                best = p;
            }
        }

        return best;
    }
}
