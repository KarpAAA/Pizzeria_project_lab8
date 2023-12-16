package com.example.map_service.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepDTO {
    private String name;
    private boolean ifMade;
    private CookDTO cook = null;

    public StepDTO(String name, boolean ifMade) {
        this.name = name;
        this.ifMade = ifMade;
    }
}

