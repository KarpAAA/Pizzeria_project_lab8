package com.example.lab_08_java.services;

import com.example.lab_08_java.data.Restaurant;
import com.example.lab_08_java.data.dtos.CookDTO;
import com.example.lab_08_java.entities.restaurant.Cook;
import com.example.lab_08_java.entities.restaurant.Step;
import com.example.lab_08_java.entities.user.User;
import com.example.lab_08_java.models.cook.CreateCookRequest;
import com.example.lab_08_java.models.cook.ReleaseCookRequest;
import com.example.lab_08_java.models.cook.UpdateCookStateRequest;
import com.example.lab_08_java.repositories.CookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CookServices {
    private final Restaurant restaurant;
    private final CookRepository cookRepository;
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;


    public void addCookToRestaurant(CookDTO cook) {
        restaurant.getCooks().add(cook);
    }

    public boolean releaseCook(ReleaseCookRequest releaseCookRequest) {
        cookRepository.deleteById((long) releaseCookRequest.getCookId());
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
        Cook cook = cookRepository.findById((long) updateCookStateRequest.getCookId()).get();
        cook.setWorkState(updateCookStateRequest.isWorkingState() ? Cook.WORK_STATE.WORKING : Cook.WORK_STATE.NOT_WORKING);
        cookRepository.save(cook);
        return true;
    }

    public CookDTO getCookByUsername(String username) {
        User user = userServices.getUserByUsername(username);
        return cookToCookDTO(cookRepository.getCookByUser(user));
    }

    private CookDTO cookToCookDTO(Cook cook){
        return new CookDTO(cook.getId(),cook.getName(),cook.getSalary(),
                cook.getAbilities().stream().distinct().map(Step::getName).toList(),
                cook.getWorkState() == Cook.WORK_STATE.WORKING);
    }

    public void createCook(CreateCookRequest createCookRequest) {
        cookRepository.save(new Cook(
                new User(createCookRequest.getLogin(),
                        passwordEncoder.encode(createCookRequest.getPassword()),
                        createCookRequest.getAge(), User.Role.COOK),
                createCookRequest.getName(),createCookRequest.getSalary(),List.of(), Cook.WORK_STATE.NOT_WORKING
                ));
    }
}
