package com.example.map_service.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookDTO {
    private Long id;
    private String name;
    private double salary;
    private List<String> skills;
    private boolean ifWorking;
}
