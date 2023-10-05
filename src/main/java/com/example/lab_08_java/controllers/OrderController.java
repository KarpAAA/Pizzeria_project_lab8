package com.example.lab_08_java.controllers;

import com.example.lab_08_java.models.OrderPizzaStepMadeRequest;
import com.example.lab_08_java.services.RestaurantServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final RestaurantServices restaurantServices;

    @PostMapping("/step")
    public ResponseEntity<?> orderPizzaStep(@RequestBody OrderPizzaStepMadeRequest orderPizzaStepMadeRequest){
        restaurantServices.orderPizzaMakeStep(orderPizzaStepMadeRequest);
        return ResponseEntity.ok("Made successfullu" + orderPizzaStepMadeRequest);
    }
}
