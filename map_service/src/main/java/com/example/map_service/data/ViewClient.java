package com.example.map_service.data;

import com.example.map_service.data.models.Client;
import com.example.map_service.data.models.Order;
import com.example.map_service.data.models.Paydesk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewClient {
    private Long id;
    private String name;
    private Order order;
    private FrontPosition position;
    private int orderProgress;
    private int mood;
}
