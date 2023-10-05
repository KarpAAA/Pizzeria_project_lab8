package com.example.lab_08_java.models.cook;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CookLearnSkillRequest {
    private Long cookId;
    private Long stepId;
}
