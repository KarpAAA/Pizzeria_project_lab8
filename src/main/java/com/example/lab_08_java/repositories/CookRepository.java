package com.example.lab_08_java.repositories;

import com.example.lab_08_java.entities.restaurant.Cook;
import com.example.lab_08_java.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CookRepository extends JpaRepository<Cook, Long> {
    public Cook getCookByUser(User user);
}
