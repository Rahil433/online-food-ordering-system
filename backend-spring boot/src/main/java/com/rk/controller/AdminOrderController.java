package com.rk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rk.Exception.OrderException;
import com.rk.Exception.RestaurantException;
import com.rk.model.CustomOrder;
import com.rk.service.OrderService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    // DELETE Order by ID
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) throws OrderException {
        if (orderId != null) {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order deleted with ID: " + orderId);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET All Orders for a Restaurant (Optional Filter by Order Status)
    @GetMapping("/order/restaurant/{restaurantId}")
    public ResponseEntity<List<CustomOrder>> getAllRestaurantOrders(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String order_status) throws OrderException, RestaurantException {

        List<CustomOrder> orders = orderService.getOrdersOfRestaurant(restaurantId, order_status);
        return ResponseEntity.ok(orders);
    }

    // UPDATE Order Status
    @PutMapping("/orders/{orderId}/{orderStatus}")
    public ResponseEntity<CustomOrder> updateOrders(
            @PathVariable Long orderId,
            @PathVariable String orderStatus) throws OrderException, RestaurantException {

        CustomOrder updatedOrder = orderService.updateOrder(orderId, orderStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
