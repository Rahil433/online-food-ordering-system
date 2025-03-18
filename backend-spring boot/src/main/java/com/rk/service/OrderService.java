package com.rk.service;

import java.util.List;

import com.rk.Exception.CartException;
import com.rk.Exception.OrderException;
import com.rk.Exception.RestaurantException;
import com.rk.Exception.UserException;
import com.rk.model.CustomOrder;
import com.rk.model.PaymentResponse;
import com.rk.model.User;
import com.rk.request.CreateOrderRequest;

public interface OrderService {
    
    // Removed StripeException (no longer needed with Razorpay)
    public PaymentResponse createOrder(CreateOrderRequest order, User user) throws UserException, RestaurantException, CartException;
    
    public CustomOrder updateOrder(Long orderId, String orderStatus) throws OrderException;
    
    public void cancelOrder(Long orderId) throws OrderException;
    
    public List<CustomOrder> getUserOrders(Long userId) throws OrderException;
    
    public List<CustomOrder> getOrdersOfRestaurant(Long restaurantId, String orderStatus) throws OrderException, RestaurantException;

}
