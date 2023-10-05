package com.example.lab_08_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class Lab08JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab08JavaApplication.class, args);
    }

}
