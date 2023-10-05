package com.example.lab_08_java.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
public class Paydesk {
    private String name;
    private List<Client> clients;

    private Availability availability;

    public enum Availability {
        AVAILABLE,
        NOT_AVAILABLE
    }
}
