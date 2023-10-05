package com.example.lab_08_java.services;

import com.example.lab_08_java.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServices {
    private final PizzaServices pizzaServices;
    private final Restaurant restaurant;
    private static int orderNumber = 1;


    public Client createNewClient() {
        List<PizzaDTO> orderPizzas = randomOrder();
        Order order = new Order(
                orderNumber, orderPizzas,
                orderPizzas
                        .stream()
                        .map(PizzaDTO::getPrice)
                        .reduce(0, Integer::sum),
                false
        );
        Client c = new Client(UUID.randomUUID().getLeastSignificantBits(), "Client " + orderNumber++, order);
        order.setClientName(c.getName());
        restaurant.getClients().add(c);
        return c;
    }

    private List<PizzaDTO> randomOrder() {
        List<PizzaDTO> all = pizzaServices.getPizzaList();
        Random random = new Random();
        int pizzaAmount = random.nextInt(1, 5);

        List<PizzaDTO> resultList = new ArrayList<>();
        for (int i = 0; i < pizzaAmount; i++) {
            int randomNumber = random.nextInt(all.size());
            PizzaDTO pizza = all.get(randomNumber);
            List<StepDTO> steps = new ArrayList<>();
            pizza.getNeedSteps().forEach(step -> steps.add(new StepDTO(step.getName(),step.isIfMade())));
            resultList.add(new PizzaDTO(pizza.getCreationTime(),steps,pizza.getName(), pizza.getPrice()));
        }
        return resultList;
    }
}
