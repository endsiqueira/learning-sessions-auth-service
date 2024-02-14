package com.learning.sessions.authservice.controller;

import com.learning.sessions.authservice.model.auth.User;
import com.learning.sessions.authservice.model.auth.dto.UserLoginDto;
import com.learning.sessions.authservice.model.auth.dto.UserRegistrationDto;
import com.learning.sessions.authservice.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private VaultTemplate vaultTemplate;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User user = authenticationService.registerUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
        try {
            String jwt = authenticationService.login(loginDto.getUsername(), loginDto.getPassword());
            return ResponseEntity.ok().body(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação: " + e.getMessage());
        }
    }

    // Assume que a chave secreta está armazenada no caminho 'secret/data/jwt' sob a chave 'jwt-secret-key'
    private String getSecretKey() {
        VaultResponseSupport<Map> response = vaultTemplate.read("secret/data/jwt", Map.class);
        if (response != null && response.getData() != null) {
            Map data = (Map) response.getData().get("data");
            if (data != null && data.get("jwt-secret-key") != null) {
                return (String) data.get("jwt-secret-key");
            }
        }
        throw new IllegalStateException("Não foi possível recuperar a chave secreta do Vault");
    }

    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        try {
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                String token = tokenHeader.substring(7);
                String secretKey = getSecretKey(); // Recupera a chave secreta do Vault
                // Valida o token com a chave recuperada
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(token)
                        .getBody();
                return ResponseEntity.ok().body(true);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }
    }
}
