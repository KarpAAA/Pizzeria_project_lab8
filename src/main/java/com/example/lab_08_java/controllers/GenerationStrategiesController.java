package com.example.lab_08_java.controllers;


import com.example.lab_08_java.services.ScheduledServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/strategies")
public class GenerationStrategiesController {
    private final ScheduledServices scheduledServices;


    @GetMapping("/default")
    public void changeGenerationStrategyToDefault(){
        scheduledServices.changeStrategyToDefault();
    }

    @GetMapping("/other")
    public void changeGenerationStrategyToOther(){
        scheduledServices.changeStrategyToOther();
    }
}

