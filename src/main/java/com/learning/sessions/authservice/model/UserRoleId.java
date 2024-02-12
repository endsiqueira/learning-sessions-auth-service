package com.learning.sessions.authservice.model;

import lombok.*;

import java.io.Serializable;

@Data
public class UserRoleId implements Serializable {
    private Long user;
    private Long role;
}
