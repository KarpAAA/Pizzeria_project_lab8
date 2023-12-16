package com.example.map_service.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pizza {
    private Long id;
    private Long creationTime;
    private List<StepDTO> needSteps;
    private String name;
    private int price;
}