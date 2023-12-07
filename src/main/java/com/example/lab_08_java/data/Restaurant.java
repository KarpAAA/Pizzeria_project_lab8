package com.example.lab_08_java.data;


import com.example.lab_08_java.data.dtos.CookDTO;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.data.generation.DefaultGenerationStrategy;
import com.example.lab_08_java.data.generation.GenerationStrategy;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@RequiredArgsConstructor
public class Restaurant {
    private List<Paydesk> paydesks = new ArrayList<>();
    private List<CookDTO> cooks = new ArrayList<>();
    private List<PizzaDTO> menu = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Order> currentOrders = new ArrayList<>();
    private Statistic stat = new Statistic();

}
