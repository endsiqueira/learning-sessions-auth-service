package com.learning.sessions.authservice.service;

import com.learning.sessions.authservice.model.User;
import com.learning.sessions.authservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String email) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setActive(true);
        return userRepository.save(newUser);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + username));

        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            return generateJwtToken(user);
        } else {
            throw new RuntimeException("Senha inválida");
        }
    }

    private String generateJwtToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 3600000;
        Date exp = new Date(expMillis);

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

}
