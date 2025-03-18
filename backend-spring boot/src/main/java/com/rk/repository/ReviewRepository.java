package com.rk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rk.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
