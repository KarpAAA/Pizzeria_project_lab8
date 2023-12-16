package com.example.map_service.data.models;

import com.example.map_service.data.ViewClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paydesk {
    private int index;
    private String name;
    private List<Client> clients;

    private Availability availability;

    public enum Availability {
        AVAILABLE,
        NOT_AVAILABLE
    }
}
