package com.example.lab_08_java.services;

import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.models.QueueRequest;
import com.example.lab_08_java.models.paydesks.DeletePaydeskRequest;
import com.example.lab_08_java.models.paydesks.UpdatePaydeskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaydeskServices {

    public boolean addPaydesks(Restaurant restaurant) {
        return restaurant
                .getPaydesks()
                .add(new Paydesk("Casa" + restaurant.getPaydesks().size(), List.of(), Paydesk.Availability.NOT_AVAILABLE));
    }

    public boolean deletePaydesk(DeletePaydeskRequest deleteRequest, Restaurant restaurant) {
        restaurant
                .getPaydesks()
                .remove(deleteRequest.getPaydeskIndex());
        return true;
    }

    public boolean updatePaydesk(UpdatePaydeskRequest updatePaydeskRequest, Restaurant restaurant) {
        restaurant
                .getPaydesks()
                .get(updatePaydeskRequest.getPaydeskIndex())
                .setAvailability(updatePaydeskRequest.getAvailability());
        return true;
    }

    public void standToQueue(QueueRequest queueRequest, Restaurant restaurant) {
        if(!restaurant.getClients().get(queueRequest.getClientIndex()).getOrder().isCompleted()){
            restaurant
                    .getPaydesks()
                    .get(queueRequest.getPaydeskIndex())
                    .getClients()
                    .add(restaurant.getClients().get(queueRequest.getClientIndex()));
        }
    }

    public void leaveFromQueue(QueueRequest queueRequest, Restaurant restaurant) {
        restaurant
                .getPaydesks()
                .get(queueRequest.getPaydeskIndex())
                .getClients()
                .remove(restaurant.getClients().get(queueRequest.getClientIndex()));
    }
}
