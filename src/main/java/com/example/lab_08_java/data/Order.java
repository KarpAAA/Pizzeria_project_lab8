package com.example.lab_08_java.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private int number;
    private List<PizzaDTO> pizzaList;
    private int sum;
    private boolean isCompleted;
    private String clientName;

    public Order(int number, List<PizzaDTO> pizzaList, int sum, boolean isCompleted) {
        this.number = number;
        this.pizzaList = pizzaList;
        this.sum = sum;
        this.isCompleted = isCompleted;
    }
}
