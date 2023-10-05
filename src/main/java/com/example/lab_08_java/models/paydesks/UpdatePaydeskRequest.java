package com.example.lab_08_java.models.paydesks;

import com.example.lab_08_java.data.Paydesk;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePaydeskRequest {
    private int paydeskIndex;
    private Paydesk.Availability availability;
}
