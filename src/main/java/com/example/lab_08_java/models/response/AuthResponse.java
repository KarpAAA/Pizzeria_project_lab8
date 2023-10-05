package com.example.lab_08_java.models.response;

import com.example.lab_08_java.data.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private JwtToken jwtToken;
    private UserDTO user;
}
