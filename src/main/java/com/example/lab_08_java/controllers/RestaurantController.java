package com.example.lab_08_java.controllers;

import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.services.RestaurantServices;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServices restaurantServices;
    private final Restaurant restaurant;

//    @GetMapping()
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<Restaurant> getRestaurant(){
//        restaurantServices.reloadRestaurantState();
//        return ResponseEntity.ok(restaurant);
//    }

}
