package com.example.lab_08_java.entities.restaurant;


import com.example.lab_08_java.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.Duration;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pizza_list")
@NoArgsConstructor()
@AllArgsConstructor()
@AttributeOverride(name = "id", column = @Column(name = "pizza_id"))
public class Pizza extends BaseEntity {

    @Column(name = "creation_time")
    private Long creationTime;

    @Column(name = "pizza_name")
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "step_id")
    )
    private List<Step> needSteps;

    private int price;

    public Pizza(Long id, Long creationTime, String name, List<Step> needSteps, int price) {
        this.setId(id);
        this.creationTime = creationTime;
        this.name = name;
        this.needSteps = needSteps;
        this.price = price;
    }
}
