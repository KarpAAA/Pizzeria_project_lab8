package com.example.lab_08_java.data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompletedOrder {
    private Order order;
    private Paydesk paydesk;
    private Client client;
}
