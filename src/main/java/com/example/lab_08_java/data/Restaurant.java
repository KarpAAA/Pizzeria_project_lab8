package com.example.lab_08_java.data;


import com.example.lab_08_java.data.dtos.CookDTO;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@RequiredArgsConstructor
public class Restaurant {
    private List<Paydesk> paydesks = new ArrayList<>();
    private List<CookDTO> cooks = new ArrayList<>();
    private List<CompletedOrder> completedOrders = new ArrayList<>();
    private List<PizzaDTO> menu = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Order> currentOrders = new ArrayList<>();
}
