package com.example.lab_08_java.entities.restaurant;

import com.example.lab_08_java.entities.base.BaseEntity;
import com.example.lab_08_java.entities.restaurant.pizza.Step;
import com.example.lab_08_java.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cooks")
@NoArgsConstructor()
@AllArgsConstructor()
@AttributeOverride(name = "id", column = @Column(name = "restaurant_id"))

public class Cook extends BaseEntity {

    @OneToOne
    private User user;

    private String name;
    private double salary;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "cook_id"),
            inverseJoinColumns = @JoinColumn(name = "step_id")
    )
    private List<Step> abilities;

    @Transient
    private WORK_STATE workState = WORK_STATE.NOT_WORKING;


    public enum WORK_STATE{
        WORKING,
        NOT_WORKING
    }
//
//    public Cook(User user, String name, double salary) {
//        this.user = user;
//        this.name = name;
//        this.salary = salary;
//    }
//
//    public Cook(String name, double salary) {
//        this.name = name;
//        this.salary = salary;
//    }
}
