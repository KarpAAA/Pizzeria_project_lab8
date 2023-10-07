package com.example.lab_08_java.entities.restaurant;

import com.example.lab_08_java.entities.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "steps")
@NoArgsConstructor()
@AllArgsConstructor()
@AttributeOverride(name = "id", column = @Column(name = "step_id"))

public class Step extends BaseEntity {
    private String name;
}
