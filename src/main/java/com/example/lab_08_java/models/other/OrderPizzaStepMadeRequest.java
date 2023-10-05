package com.example.lab_08_java.models.other;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPizzaStepMadeRequest {
    private int orderNumber;
    private int pizzaIndex;
    private int stepIndex;
}
