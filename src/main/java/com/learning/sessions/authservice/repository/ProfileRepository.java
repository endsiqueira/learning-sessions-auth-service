package com.learning.sessions.authservice.repository;

import com.learning.sessions.authservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {}

