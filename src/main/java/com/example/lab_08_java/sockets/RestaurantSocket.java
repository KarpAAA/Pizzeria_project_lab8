package com.example.lab_08_java.sockets;


import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.services.RestaurantServices;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantSocket {
    private final RestaurantServices restaurantServices;
    private final Restaurant restaurant;

    @SendTo("/topic/restaurant")
    public Restaurant sendMessage(){
        restaurantServices.reloadRestaurantState();
        return restaurant;
    }
}
