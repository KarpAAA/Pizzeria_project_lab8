package com.example.map_service.services;


import com.example.map_service.data.FrontPosition;
import com.example.map_service.data.Map;
import com.example.map_service.data.ViewClient;
import com.example.map_service.data.ViewPaydesk;
import com.example.map_service.data.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MapServices {

    @Value("${view.size.width}")
    public int width;

    @Value("${view.size.height}")
    public int height;

    private final int offsetCount = 11;
    private final int paydesksAmount = 10;
    private final int paydeskToOffset = 3;

    private final int paydeskHeight = 100;
    private final int cleintHeight = 110;

    private final int paydeskOffsetY = 30;
    private final int clientOffsetY = 10;

    public Map createMapFromRestaurant(RestaurantDTO restaurantState) {
        int offsetWidth = calculateOffsetWidth();
        int paydestWidth = calculatePaydeskWidth();

        List<ViewPaydesk> viewPaydesks = new ArrayList<>();
        int index = 0;
        for (Paydesk p : restaurantState.getPaydesks()) {
            double x = index == 0 ? offsetWidth : viewPaydesks.get(index - 1).getPosition().getX() + offsetWidth + paydestWidth;

            ViewPaydesk viewPaydesk = new ViewPaydesk(
                    p.getName(),
                    p.getAvailability(),
                    new FrontPosition(x, paydeskOffsetY, paydestWidth, this.paydeskHeight));
            viewPaydesks.add(viewPaydesk);
            index++;
        }
        index = 0;
        AtomicInteger finalIndex = new AtomicInteger(index);
        viewPaydesks.forEach(vp ->
                vp.setClients(
                        formViewClientListForPaydesk(restaurantState.getPaydesks().get(finalIndex.getAndIncrement()), vp)));
        return new Map(viewPaydesks);
    }

    private List<ViewClient> formViewClientListForPaydesk(Paydesk paydesk, ViewPaydesk vp) {
        List<ViewClient> clients = new ArrayList<>();

        int index = 0;
        for (Client c : paydesk.getClients()) {
            double y = index == 0 ?
                    vp.getPosition().getY() + vp.getPosition().getHeight() + paydeskOffsetY
                    : clients.get(index - 1).getPosition().getY() + cleintHeight + clientOffsetY;


            ViewClient client = new ViewClient(
                    c.getId(),
                    c.getName(),
                    c.getOrder(),
                    new FrontPosition(
                            vp.getPosition().getX() + calculateOffsetWidth(),
                            y,
                            calculateOffsetWidth() + Math.floor(calculateOffsetWidth() / 2.0),
                            this.cleintHeight),
                    calculateOrderCompletedPercent(c.getOrder()),
                    calculateMoodPercent(c)
            );
            clients.add(client);
            index++;
        }

        return clients;
    }

    private int calculateMoodPercent(Client c) {
        Long generalMills  = c.getOrder()
                .getPizzaList().stream().map(Pizza::getCreationTime)
                .reduce(0L,Long::sum);

        LocalTime finishTime = c.getOrder().getCreatedOrderTime().plus(generalMills, ChronoUnit.MILLIS);
        double leftMills = differenceInMills(LocalTime.now(), finishTime);
        if(leftMills <= 0 ) return 0;
        double res = 100.0 / generalMills.doubleValue() * leftMills;
        return (int)res;
    }

    private long differenceInMills(LocalTime startTime, LocalTime endTime){
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), startTime);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), endTime);

        Instant startInstant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();

        long startMillis = startInstant.toEpochMilli();
        long endMillis = endInstant.toEpochMilli();

        return endMillis - startMillis;
    }
    private int calculateOrderCompletedPercent(Order order) {
        int stepsCount = (int) order.getPizzaList()
                .stream()
                .mapToLong(p -> p.getNeedSteps().size())
                .sum();

        int stepsCompletedCount = (int) order.getPizzaList()
                .stream()
                .mapToLong(p -> p.getNeedSteps().stream().filter(StepDTO::isIfMade).count())
                .sum();
        return 100 / stepsCount * stepsCompletedCount;
    }

    private int calculateOffsetWidth() {
        int xCount = paydesksAmount * paydeskToOffset + offsetCount;
        return width / xCount;
    }

    private int calculatePaydeskWidth() {
        int xCount = paydesksAmount * paydeskToOffset + offsetCount;
        return (width / xCount) * paydeskToOffset;
    }
}
