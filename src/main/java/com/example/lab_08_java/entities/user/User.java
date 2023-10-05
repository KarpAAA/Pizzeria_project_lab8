package com.example.lab_08_java.entities.user;


import com.example.lab_08_java.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    private String username;
    private String password;
    private int age;
    private Role role;

    public enum Role{
        USER,
        COOK,
        ADMIN;
    }
}
