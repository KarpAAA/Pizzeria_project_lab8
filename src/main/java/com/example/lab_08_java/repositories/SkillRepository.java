package com.example.lab_08_java.repositories;

import com.example.lab_08_java.entities.restaurant.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Step,Long> {
}
