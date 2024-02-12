package com.learning.sessions.authservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {
    private String username;
    private String password;
}