package com.rk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rk.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
