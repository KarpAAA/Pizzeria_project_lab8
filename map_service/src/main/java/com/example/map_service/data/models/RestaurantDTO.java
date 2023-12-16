package com.example.map_service.data.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantDTO {
    private List<Paydesk> paydesks;
}
