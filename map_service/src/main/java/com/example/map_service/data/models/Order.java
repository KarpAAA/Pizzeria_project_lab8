package com.example.map_service.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Comparable<Order>{
    private int number;
    private List<Pizza> pizzaList;
    private int sum;
    private boolean isCompleted;
    private String clientName;

    private LocalTime createdOrderTime;
    private LocalTime finishedOrderTime;
    @Override
    public int compareTo(Order o) {
        return this.number - o.number;
    }
}
