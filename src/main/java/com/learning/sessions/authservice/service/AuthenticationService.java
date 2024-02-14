package com.learning.sessions.authservice.service;

import com.learning.sessions.authservice.model.auth.User;
import com.learning.sessions.authservice.repository.auth.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import java.util.Date;
import java.util.HashMap;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VaultTemplate vaultTemplate;

    private final String secretPath = "secret/data/jwt";

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
        long expMillis = nowMillis + 3600000; // Token expira em 1 hora
        Date exp = new Date(expMillis);

        VaultResponseSupport<HashMap> response = vaultTemplate.read(secretPath, HashMap.class);
        if (response == null || response.getData().get("jwt-secret-key") == null) {
            throw new IllegalStateException("Não foi possível recuperar a chave secreta do Vault");
        }
        String secretKey = response.getData().get("jwt-secret-key").toString();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
