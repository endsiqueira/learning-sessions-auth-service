package com.learning.sessions.authservice.repository.auth;

import com.learning.sessions.authservice.model.auth.UserRole;
import com.learning.sessions.authservice.model.auth.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {}

