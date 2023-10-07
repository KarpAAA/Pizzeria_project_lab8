package com.example.lab_08_java.services;

import com.example.lab_08_java.data.*;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.data.dtos.StepDTO;
import com.example.lab_08_java.models.other.OrderPizzaStepMadeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RestaurantServices {
    private final CookServices cookServices;
    private final PizzaServices pizzaServices;
    private final Restaurant restaurant;

    public void reloadRestaurantState() {
        List<PizzaDTO> pizzas = pizzaServices.getPizzaList();

        if (restaurant.getPaydesks().size() == 0) {
            IntStream.range(1, 11).forEach(
                    index ->
                            restaurant
                                    .getPaydesks()
                                    .add(new Paydesk("CASA " + index, new ArrayList<>(), Paydesk.Availability.AVAILABLE))
            );

        }


        restaurant.setCooks(cookServices.getAllCooks());
        restaurant.setMenu(pizzas);

        List<Order> ordersList = new ArrayList<>();
        restaurant.getPaydesks()
                .forEach(p -> p.getClients().stream().findFirst().ifPresent(c ->{
                    if(!c.getOrder().isCompleted())ordersList.add(c.getOrder());
                }));

        restaurant.setCurrentOrders(ordersList);

    }

    public void orderPizzaMakeStep(OrderPizzaStepMadeRequest orderPizzaStepMadeRequest) {
        Order order = restaurant.getClients()
                .stream()
                .map(Client::getOrder)
                .filter(o -> o.getNumber() == orderPizzaStepMadeRequest.getOrderNumber())
                .findFirst().get();
        order.getPizzaList()
                .get(orderPizzaStepMadeRequest.getPizzaIndex())
                .getNeedSteps()
                .get(orderPizzaStepMadeRequest.getStepIndex()).setIfMade(true);

        if(checkIfOrderCompleted(order.getNumber())){
            Paydesk paydesk =  restaurant
                    .getPaydesks()
                    .stream()
                    .filter(p -> p.getClients()
                            .stream().map(Client::getOrder)
                            .anyMatch(o -> o.getNumber() == order.getNumber()))
                    .findFirst()
                    .get();
            Client client = getCompletedOrdersClient(order.getNumber());

            restaurant.setIncome(restaurant.getIncome() + order.getPizzaList().stream().map(PizzaDTO::getPrice).reduce(0, Integer::sum));
            restaurant.getCompletedOrders()
                    .add(new CompletedOrder(order,paydesk,client));

            setOrderCompleted(client.getOrder());
            restaurant.getCurrentOrders().removeIf(o -> o.getNumber() == order.getNumber());
            paydesk.getClients().remove(client);
        }
    }


    private void setOrderCompleted(Order order){
        order.setCompleted(true);
        order.setFinishedOrderTime(LocalTime.now());
    }
    private Client getCompletedOrdersClient(int orderNumber){
        return  restaurant
                .getClients()
                .stream()
                .filter(c -> c.getOrder()
                        .getNumber() == orderNumber)
                .findFirst()
                .get();
    }
    private boolean checkIfOrderCompleted(int orderNumber){
        return restaurant
                .getCurrentOrders()
                .stream()
                .filter(o -> o.getNumber() == orderNumber)
                .flatMap(order -> order.getPizzaList().stream().flatMap(pizza -> pizza.getNeedSteps().stream()))
                .allMatch(StepDTO::isIfMade);

    }
}
