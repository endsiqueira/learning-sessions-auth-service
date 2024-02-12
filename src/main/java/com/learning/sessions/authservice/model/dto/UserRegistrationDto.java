package com.learning.sessions.authservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
}