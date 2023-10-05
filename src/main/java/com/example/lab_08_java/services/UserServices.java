package com.example.lab_08_java.services;

import com.example.lab_08_java.data.UserDTO;
import com.example.lab_08_java.entities.user.User;
import com.example.lab_08_java.models.AuthResponse;
import com.example.lab_08_java.models.JwtToken;
import com.example.lab_08_java.models.user.LoginUser;
import com.example.lab_08_java.models.user.RegisterUser;
import com.example.lab_08_java.repositories.UserRepository;
import com.example.lab_08_java.services.security.JwtServices;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServices implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserFromModel(RegisterUser registerUser, User.Role role){
        return new User(registerUser.getUsername(),
                passwordEncoder.encode(registerUser.getPassword()),
                registerUser.getAge(),
                role);
    }

    public UserDTO authUserRequest(LoginUser loginUser){
        User user = userRepository.findByUsername(loginUser.getUsername());

        return new UserDTO(user.getUsername(),user.getRole());
    }
}
