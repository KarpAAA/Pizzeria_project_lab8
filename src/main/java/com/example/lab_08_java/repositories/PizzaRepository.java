package com.example.lab_08_java.repositories;

import com.example.lab_08_java.entities.restaurant.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza,Long> {
}
