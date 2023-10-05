package com.example.lab_08_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
@EnableAspectJAutoProxy
public class Lab08JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab08JavaApplication.class, args);
    }

}
