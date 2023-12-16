package com.example.lab_08_java.services;

import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.models.other.QueueRequest;
import com.example.lab_08_java.models.paydesks.UpdatePaydeskRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaydeskServices {

    /**
     * Updates the availability of a paydesk in a restaurant.
     *
     * @param updatePaydeskRequest the request containing the paydesk index and the new availability
     * @param restaurant           the restaurant object containing the paydesks list
     * @return true if the paydesk was updated, false otherwise
     */
    public boolean updatePaydesk(UpdatePaydeskRequest updatePaydeskRequest, Restaurant restaurant) {
        Paydesk p = restaurant
                .getPaydesks()
                .get(updatePaydeskRequest.getPaydeskIndex());
        p.setAvailability(updatePaydeskRequest.getAvailability());
        return true;
    }

    /**
     * This function is used to set a client's chosen paydesk index to the given index in the queue request.
     * If the client's order is not completed, the chosen paydesk index will be set.
     * If the client's order is completed, the chosen paydesk index will not be changed.
     *
     * @param queueRequest the request containing the client index and the paydesk index
     * @param restaurant the restaurant object containing the clients and paydesks list
     */
    public void standToQueue(QueueRequest queueRequest, Restaurant restaurant) {
        if (!restaurant.getClients().get(queueRequest.getClientIndex()).getOrder().isCompleted()) {
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

    public Paydesk findBestPaydesk(Restaurant restaurant) {
        Map<Paydesk, Long> creationTimes = restaurant
                .getPaydesks()
                .stream()
                .filter(p -> p.getAvailability() == Paydesk.Availability.AVAILABLE)
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
