package com.example.map_service.data;

import com.example.map_service.data.models.Paydesk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPaydesk {
    private String name;
    private List<ViewClient> clients;
    private Paydesk.Availability availability;
    private FrontPosition position;

    public ViewPaydesk(String name, Paydesk.Availability availability, FrontPosition position) {
        this.name = name;
        this.availability = availability;
        this.position = position;
    }
}
