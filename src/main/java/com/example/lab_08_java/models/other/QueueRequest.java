package com.example.lab_08_java.models.other;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueRequest {
    private int paydeskIndex;
    private int clientIndex;
}
