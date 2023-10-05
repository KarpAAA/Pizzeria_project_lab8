package com.example.lab_08_java.models;

import com.example.lab_08_java.entities.restaurant.pizza.Step;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SkillsResponse {
    private List<Step> skills;
}
