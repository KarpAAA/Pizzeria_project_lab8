package com.example.lab_08_java.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private Long id;
    private String name;
    private Order order;
}
