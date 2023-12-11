package com.example.lab_08_java.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Client{
    private Long id;
    private String name;
    private Order order;
    private int chosenPaydesk;
}
