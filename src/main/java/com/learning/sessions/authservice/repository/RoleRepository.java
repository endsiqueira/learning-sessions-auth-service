package com.learning.sessions.authservice.repository;

import com.learning.sessions.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}

