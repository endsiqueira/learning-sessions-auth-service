package com.learning.sessions.authservice.repository;

import com.learning.sessions.authservice.model.UserRole;
import com.learning.sessions.authservice.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {}

