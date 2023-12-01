package com.example.lab_08_java.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class StepDTO {
    private String name;
    private boolean ifMade;
    private CookDTO cook = null;

    public StepDTO(String name, boolean ifMade) {
        this.name = name;
        this.ifMade = ifMade;
    }
}

