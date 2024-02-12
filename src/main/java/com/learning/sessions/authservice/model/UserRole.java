package com.learning.sessions.authservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "UserRoles")
@IdClass(UserRoleId.class)
@Data
@NoArgsConstructor
public class UserRole {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
