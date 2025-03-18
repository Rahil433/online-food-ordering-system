package com.rk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rk.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
