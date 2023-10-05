package com.example.lab_08_java.services;

import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.data.dtos.StepDTO;
import com.example.lab_08_java.repositories.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaServices {

    private final PizzaRepository pizzaRepository;

    public List<PizzaDTO> getPizzaList() {
        return pizzaRepository
                .findAll()
                .stream()
                .map(pizza ->
                        new PizzaDTO(pizza
                                .getCreationTime(),
                                pizza.getNeedSteps().stream().map(step -> new StepDTO(step.getName(),false)).toList(),
                                pizza.getName(),
                                pizza.getPrice()))
                .toList();
    }

}
