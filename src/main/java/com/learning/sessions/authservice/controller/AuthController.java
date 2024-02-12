package com.learning.sessions.authservice.controller;

import com.learning.sessions.authservice.model.User;
import com.learning.sessions.authservice.model.dto.UserLoginDto;
import com.learning.sessions.authservice.model.dto.UserRegistrationDto;
import com.learning.sessions.authservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User user = authenticationService.registerUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
        // Placeholder para implementação futura
        // Aqui ficara a lógica para autenticar o usuário e retornar um JWT
        // Por agora, vamos retornar uma resposta de placeholder
        return ResponseEntity.ok().body("Login functionality under construction.");
    }
}
