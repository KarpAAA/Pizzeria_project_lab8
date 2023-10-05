package com.example.lab_08_java.models.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUser {
    private String username;
    private String password;
}
