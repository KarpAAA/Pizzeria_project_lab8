package com.example.lab_08_java.controllers;

import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.services.ClientServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientServices clientServices;

    @PostMapping("/new")
    public ResponseEntity<?> createNewClient(){
        Client client = clientServices.createNewClient();
        return ResponseEntity.ok("Clilent was created!\n" + client);
    }
}
