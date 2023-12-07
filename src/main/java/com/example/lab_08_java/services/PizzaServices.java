package com.example.lab_08_java.services;

import com.example.lab_08_java.models.other.SavePizzaRequest;
import com.example.lab_08_java.data.dtos.PizzaDTO;
import com.example.lab_08_java.data.dtos.StepDTO;
import com.example.lab_08_java.entities.restaurant.Pizza;
import com.example.lab_08_java.entities.restaurant.Step;
import com.example.lab_08_java.models.other.DeletePizzaRequest;
import com.example.lab_08_java.repositories.PizzaRepository;
import com.example.lab_08_java.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PizzaServices {
    private final PizzaRepository pizzaRepository;
    private final SkillRepository skillRepository;

    public List<PizzaDTO> getPizzaList() {
        return pizzaRepository
                .findAll()
                .stream()
                .map(pizza ->
                        new PizzaDTO(pizza.getId(), pizza
                                .getCreationTime(),
                                pizza.getNeedSteps().stream().map(step -> new StepDTO(step.getName(), false)).toList(),
                                pizza.getName(),
                                pizza.getPrice()))
                .toList();
    }

    public void savePizza(SavePizzaRequest savePizzaRequest) {
        List<Step> steps = new ArrayList<>();
        List<String> stepsNeedNames = Stream.of(savePizzaRequest.getSteps().split("\n")).toList();
        for (var stepName : stepsNeedNames) {
            steps.add(skillRepository.findByName(stepName).orElse(new Step(stepName)));
        }
        pizzaRepository.save(
                new Pizza(savePizzaRequest.getId(),
                        savePizzaRequest.getCreationTime() * 60000L,
                        savePizzaRequest.getName(),
                        steps,
                        savePizzaRequest.getPrice()));
    }

    public void deletePizza(DeletePizzaRequest deletePizzaRequest) {
        pizzaRepository.deleteById((long)deletePizzaRequest.getId());
    }
}
