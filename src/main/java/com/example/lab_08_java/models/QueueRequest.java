package com.example.lab_08_java.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueRequest {
    private int paydeskIndex;
    private int clientIndex;
}
