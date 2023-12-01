package com.example.lab_08_java;

import com.example.lab_08_java.services.RestaurantServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@EnableScheduling
public class Lab08JavaApplication {
    private final RestaurantServices restaurantServices;

    public static void main(String[] args) {
        SpringApplication.run(Lab08JavaApplication.class, args);
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
           restaurantServices.reloadRestaurantState();
        };
    }
}
