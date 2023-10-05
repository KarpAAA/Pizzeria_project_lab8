package com.example.lab_08_java.models;

import com.example.lab_08_java.entities.restaurant.Cook;
import com.example.lab_08_java.entities.restaurant.pizza.Step;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CookLearnSkillRequest {
    private Long cookId;
    private Long stepId;
}
