package com.example.lab_08_java.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantDTO implements Serializable {
    private List<Paydesk> paydesks;
}

