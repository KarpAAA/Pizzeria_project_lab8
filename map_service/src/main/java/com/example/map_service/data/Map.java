package com.example.map_service.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class Map {
    public List<ViewPaydesk> paydesks;
}
