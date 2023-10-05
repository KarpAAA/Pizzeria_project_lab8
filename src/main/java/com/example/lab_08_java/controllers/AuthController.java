package com.example.lab_08_java.controllers;


import com.example.lab_08_java.models.response.AuthResponse;
import com.example.lab_08_java.models.user.LoginUser;
import com.example.lab_08_java.services.UserServices;
import com.example.lab_08_java.services.JwtServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserServices userServices;
    private final JwtServices jwtServices;

    @PostMapping("/auth")
    public ResponseEntity<?> authUser(@RequestBody LoginUser loginUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()));

        return ResponseEntity
                .ok()
                .body(
                        new AuthResponse(jwtServices.createToken(loginUser).get()
                                , userServices.authUserRequest(loginUser))
                );
    }

}
