package com.example.lab_08_java.models.paydesks;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeletePaydeskRequest {
    private int paydeskIndex;
}
