package com.rk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rk.model.CustomOrder;

public interface OrderRepository extends JpaRepository<CustomOrder, Long> {

    @Query("SELECT o FROM CustomOrder o WHERE o.customer.id = :userId")
    List<CustomOrder> findAllUserOrders(@Param("userId") Long userId);

    @Query("SELECT o FROM CustomOrder o WHERE o.restaurant.id = :restaurantId")
    List<CustomOrder> findOrdersByRestaurantId(@Param("restaurantId") Long restaurantId);
}


