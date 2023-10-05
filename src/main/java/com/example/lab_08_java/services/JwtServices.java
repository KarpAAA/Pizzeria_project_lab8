package com.example.lab_08_java.services;

import com.example.lab_08_java.entities.user.User;
import com.example.lab_08_java.models.response.JwtToken;
import com.example.lab_08_java.models.user.LoginUser;
import com.example.lab_08_java.services.UserServices;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServices {

    private final UserServices userService;

    @Value("${jwt.lifetime}")
    private Duration duration;

    @Value("${jwt.secret}")
    private String secret;

    private final String USERNAME_CLAIMS_FIELD = "username";
    private final String ROLE_CLAIMS_FIELD = "role";

    public Optional<JwtToken> createToken(LoginUser loginUser) {
        UserDetails userDetails = userService.loadUserByUsername(loginUser.getUsername());

        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME_CLAIMS_FIELD, userDetails.getUsername());
        claims.put(ROLE_CLAIMS_FIELD, userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Date currentDate = new Date();

        String token = Jwts.builder()
                .setSubject(loginUser.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + duration.toMillis()))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret.getBytes())
                .compact();
        return Optional.of(new JwtToken(token));
    }
    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
//         Jwts
//                .parser()
//                .setSigningKey(secret.getBytes())
//                .parseClaimsJws(token)
//                .getBody();
    }
    public Collection<User.Role> getRoles(String token){
        List<String> roles = (List<String>) getClaims(token).get(ROLE_CLAIMS_FIELD);
        return roles.stream().map(r -> User.Role.valueOf(r)).toList();
    }
    public String getUsername(String token){
        return (String) getClaims(token).get(USERNAME_CLAIMS_FIELD);
    }
}
