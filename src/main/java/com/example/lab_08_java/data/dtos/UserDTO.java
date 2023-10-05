package com.example.lab_08_java.data.dtos;

import com.example.lab_08_java.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String name;
    private User.Role role;
}
