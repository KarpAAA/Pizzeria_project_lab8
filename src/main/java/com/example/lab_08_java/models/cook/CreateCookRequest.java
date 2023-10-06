package com.example.lab_08_java.models.cook;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCookRequest {
    private String login;
    private String password;
    private String name;
    private int age;
    private double salary;
}
