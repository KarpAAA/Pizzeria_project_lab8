package com.example.lab_08_java.controllers;


import com.example.lab_08_java.models.cook.ReleaseCookRequest;
import com.example.lab_08_java.models.cook.UpdateCookStateRequest;
import com.example.lab_08_java.services.CookServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cook")
public class CookController {
    private final CookServices cookServices;


    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCooks(){
        return ResponseEntity
                .ok()
                .body(cookServices.getAllCooks());
    }


    @PostMapping("/release")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> releaseCook(
            @RequestBody ReleaseCookRequest releaseCookRequest){
        cookServices.releaseCook(releaseCookRequest);
        return ResponseEntity
                .ok("Cook" + releaseCookRequest.getCookIndex() + "was released");
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCook(
            @RequestBody UpdateCookStateRequest updateCookStateRequest){
        cookServices.updateCookState(updateCookStateRequest);
        return ResponseEntity
                .ok("Cook" + updateCookStateRequest.getCookIndex() + "was released");
    }

    @GetMapping("/username")
    @PreAuthorize("hasRole('COOK')")
    public ResponseEntity<?> getCookByUsername(
            @RequestParam(name = "username") String username){
        return ResponseEntity.ok(cookServices.getCookByUsername(username));
    }
}
