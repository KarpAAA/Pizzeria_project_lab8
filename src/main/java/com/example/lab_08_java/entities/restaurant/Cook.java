package com.example.lab_08_java.entities.restaurant;

import com.example.lab_08_java.entities.base.BaseEntity;
import com.example.lab_08_java.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cooks")
@NoArgsConstructor()
@AllArgsConstructor()
@AttributeOverride(name = "id", column = @Column(name = "restaurant_id"))
public class Cook extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    private String name;
    private double salary;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            joinColumns = @JoinColumn(name = "cook_id"),
            inverseJoinColumns = @JoinColumn(name = "step_id")
    )
    private List<Step> abilities;


    private WORK_STATE workState = WORK_STATE.NOT_WORKING;


    public enum WORK_STATE{
        WORKING,
        NOT_WORKING
    }

}
