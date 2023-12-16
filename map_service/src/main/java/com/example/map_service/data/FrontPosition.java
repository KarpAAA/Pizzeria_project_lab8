package com.example.map_service.data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrontPosition {
    private double x;
    private double y;
    private double width;
    private double height;
}
