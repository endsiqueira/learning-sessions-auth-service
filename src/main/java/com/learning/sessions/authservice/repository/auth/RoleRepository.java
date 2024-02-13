package com.learning.sessions.authservice.repository.auth;

import com.learning.sessions.authservice.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}

