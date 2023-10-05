package com.example.lab_08_java.services;

import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.CookDTO;
import com.example.lab_08_java.entities.restaurant.Cook;
import com.example.lab_08_java.entities.restaurant.pizza.Step;
import com.example.lab_08_java.entities.user.User;
import com.example.lab_08_java.models.cook.RegisterCookModel;
import com.example.lab_08_java.models.cook.ReleaseCookRequest;
import com.example.lab_08_java.models.cook.UpdateCookStateRequest;
import com.example.lab_08_java.models.user.RegisterUser;
import com.example.lab_08_java.repositories.CookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CookServices {
    private final Restaurant restaurant;
    private final CookRepository cookRepository;
    private final UserServices userServices;


    public void addCookToRestaurant(CookDTO cook) {
        restaurant.getCooks().add(cook);
    }

    public boolean releaseCook(ReleaseCookRequest releaseCookRequest) {
        restaurant
                .getCooks()
                .remove(releaseCookRequest.getCookIndex());
        return true;
    }

    public List<CookDTO> getAllCooks() {
        return cookRepository
                .findAll()
                .stream()
                .map( this::cookToCookDTO)
                .collect(Collectors.toList());
    }
    public CookDTO getCook(int index) {
        return restaurant.getCooks().get(index);
    }

    public boolean updateCookState(UpdateCookStateRequest updateCookStateRequest) {
        restaurant
                .getCooks()
                .get(updateCookStateRequest.getCookIndex())
                .setIfWorking(updateCookStateRequest.isWorkingState());
        return true;
    }

    public CookDTO getCookByUsername(String username) {
        User user = userServices.getUserByUsername(username);
        return cookToCookDTO(cookRepository.getCookByUser(user));
    }

    private CookDTO cookToCookDTO(Cook cook){
        return new CookDTO(cook.getId(),cook.getName(),cook.getSalary(),
                cook.getAbilities().stream().map(Step::getName).toList(),
                true);
    }
}
