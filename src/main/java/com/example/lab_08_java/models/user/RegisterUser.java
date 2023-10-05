package com.example.lab_08_java.models.user;


import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUser {

    @Size(min = 8, message = "Username should contain at least 8 symbols")
    private String username;

    @Size(min = 8, message = "Password should contain at least 8 symbols")
    private String password;

    private int age;

}
