package com.example.lab_08_java.models.other;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavePizzaRequest {
    private Long id;
    private String name;
    private String steps;
    private int price;
    private int creationTime;
}
