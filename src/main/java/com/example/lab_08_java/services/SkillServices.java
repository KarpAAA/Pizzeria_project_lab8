package com.example.lab_08_java.services;

import com.example.lab_08_java.entities.restaurant.Cook;
import com.example.lab_08_java.entities.restaurant.pizza.Step;
import com.example.lab_08_java.models.CookLearnSkillRequest;
import com.example.lab_08_java.repositories.CookRepository;
import com.example.lab_08_java.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServices {
    private final SkillRepository skillRepository;
    private final CookRepository cookRepository;
    public List<Step> getAllSkills() {
        return skillRepository.findAll();
    }

    public void cookLearnSkill(CookLearnSkillRequest cookLearnSkillRequest) {
        Step skill = skillRepository.findById(cookLearnSkillRequest.getStepId()).get();
        Cook cook = cookRepository.findById(cookLearnSkillRequest.getCookId()).get();
        cook.getAbilities().add(skill);
        cookRepository.save(cook);
    }
}
