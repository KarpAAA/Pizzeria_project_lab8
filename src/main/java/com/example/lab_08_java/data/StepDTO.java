package com.example.lab_08_java.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StepDTO {
    private String name;
    private boolean ifMade;
}
