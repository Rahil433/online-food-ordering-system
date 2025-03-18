package com.rk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rk.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


//    CartItem findByFoodIsContaining

}
