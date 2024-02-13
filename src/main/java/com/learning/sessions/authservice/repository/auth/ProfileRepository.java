package com.learning.sessions.authservice.repository.auth;

import com.learning.sessions.authservice.model.auth.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {}

