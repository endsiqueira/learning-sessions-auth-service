package com.learning.sessions.authservice.repository;


import com.learning.sessions.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
