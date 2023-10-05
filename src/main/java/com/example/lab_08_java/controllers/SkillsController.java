package com.example.lab_08_java.controllers;

import com.example.lab_08_java.models.CookLearnSkillRequest;
import com.example.lab_08_java.models.SkillsResponse;
import com.example.lab_08_java.repositories.SkillRepository;
import com.example.lab_08_java.services.SkillServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillsController {
    private final SkillServices skillServices;

    @GetMapping()
    public ResponseEntity<?> getAllSkills(){
        return ResponseEntity.ok(new SkillsResponse(skillServices.getAllSkills()));
    }

    @PostMapping("/new")
    public ResponseEntity<?> cookLearnSkill(@RequestBody CookLearnSkillRequest cookLearnSkillRequest){
        skillServices.cookLearnSkill(cookLearnSkillRequest);
        return ResponseEntity.ok("Skill was learned successfully" + cookLearnSkillRequest.toString());
    }
}

