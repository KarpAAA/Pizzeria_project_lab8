package com.example.lab_08_java.controllers;


import com.example.lab_08_java.data.SavePizzaRequest;
import com.example.lab_08_java.models.DeletePizzaRequest;
import com.example.lab_08_java.services.PizzaServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pizza")
@RequiredArgsConstructor
public class PizzaController {
    private final PizzaServices pizzaServices;

    @PostMapping()
    public ResponseEntity<?> createNewPizza(@RequestBody SavePizzaRequest savePizzaRequest){
        pizzaServices.savePizza(savePizzaRequest);
        return ResponseEntity.ok("Pizza was created" + savePizzaRequest);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePizza(@RequestBody DeletePizzaRequest deletePizzaRequest){
        pizzaServices.deletePizza(deletePizzaRequest);
        return ResponseEntity.ok("Pizza was created" + deletePizzaRequest);
    }
}
