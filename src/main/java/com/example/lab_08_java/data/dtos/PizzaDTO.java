package com.example.lab_08_java.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
public class PizzaDTO {
    private Long id;
    private Long creationTime;
    private List<StepDTO> needSteps;
    private String name;
    private int price;
}
