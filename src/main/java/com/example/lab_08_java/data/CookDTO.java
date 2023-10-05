package com.example.lab_08_java.data;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CookDTO {
    private Long id;
    private String name;
    private double salary;
    private List<String> skills;
    private boolean ifWorking;
}
