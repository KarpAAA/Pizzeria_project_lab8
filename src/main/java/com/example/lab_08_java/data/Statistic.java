package com.example.lab_08_java.data;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Statistic {
    private List<CompletedOrder> completedOrders = new ArrayList<>();
    private List<Order> failedOrders = new ArrayList<>();
    private int income = 0;
    private int lostIncome = 0;
    private double ratingIn5;

    public double getRatingIn5(){
        double generalOrders = completedOrders.size() + failedOrders.size();
        if(generalOrders == 0) return 5;
        return completedOrders.size() / generalOrders * 5;
    }

}
