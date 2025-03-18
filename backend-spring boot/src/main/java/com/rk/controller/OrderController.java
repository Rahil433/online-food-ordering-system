package com.rk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rk.Exception.CartException;
import com.rk.Exception.OrderException;
import com.rk.Exception.RestaurantException;
import com.rk.Exception.UserException;
import com.rk.model.CustomOrder;
import com.rk.model.PaymentResponse;
import com.rk.model.User;
import com.rk.request.CreateOrderRequest;
import com.rk.service.OrderService;
import com.rk.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(
            @RequestBody CreateOrderRequest order,
            @RequestHeader("Authorization") String jwt)
            throws UserException, RestaurantException, CartException, OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        System.out.println("req user " + user.getEmail());

        if (order != null) {
            PaymentResponse res = orderService.createOrder(order, user);
            return ResponseEntity.ok(res);
        } else {
            throw new OrderException("Please provide valid request body");
        }
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<CustomOrder>> getAllUserOrders(
            @RequestHeader("Authorization") String jwt)
            throws OrderException, UserException {

        User user = userService.findUserProfileByJwt(jwt);

        if (user.getId() != null) {
            List<CustomOrder> userOrders = orderService.getUserOrders(user.getId());
            return ResponseEntity.ok(userOrders);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
