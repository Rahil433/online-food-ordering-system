package com.rk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.Exception.CartException;
import com.rk.Exception.OrderException;
import com.rk.Exception.RestaurantException;
import com.rk.Exception.UserException;
import com.rk.model.Address;
import com.rk.model.Cart;
import com.rk.model.CartItem;
import com.rk.model.CustomOrder;
import com.rk.model.Notification;
import com.rk.model.OrderItem;
import com.rk.model.PaymentResponse;
import com.rk.model.Restaurant;
import com.rk.model.User;
import com.rk.repository.AddressRepository;
import com.rk.repository.OrderItemRepository;
import com.rk.repository.OrderRepository;
import com.rk.repository.RestaurantRepository;
import com.rk.repository.UserRepository;
import com.rk.request.CreateOrderRequest;

@Service
public class OrderServiceImplementation implements OrderService {
    
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartSerive cartService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;  // Razorpay-compatible PaymentService

    @Autowired
    private NotificationService notificationService;

    @Override
    public PaymentResponse createOrder(CreateOrderRequest order, User user) 
            throws UserException, RestaurantException, CartException {

        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
        }
        userRepository.save(user);

        Optional<Restaurant> restaurant = restaurantRepository.findById(order.getRestaurantId());
        if (restaurant.isEmpty()) {
            throw new RestaurantException("Restaurant not found with id " + order.getRestaurantId());
        }

        CustomOrder createdOrder = new CustomOrder();
        createdOrder.setCustomer(user);
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setRestaurant(restaurant.get());

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getFood().getPrice() * cartItem.getQuantity());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setTotalAmount(totalPrice);
        createdOrder.setRestaurant(restaurant.get());
        createdOrder.setItems(orderItems);

        CustomOrder savedOrder = orderRepository.save(createdOrder);
        restaurant.get().getOrders().add(savedOrder);
        restaurantRepository.save(restaurant.get());

        // ✅ Use Razorpay's `generatePaymentLink` (via PaymentService)
        PaymentResponse paymentResponse = paymentService.generatePaymentLink(savedOrder);

        return paymentResponse;
    }

    @Override
    public void cancelOrder(Long orderId) throws OrderException {
        CustomOrder order = findOrderById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with the id " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    public CustomOrder findOrderById(Long orderId) throws OrderException {
        Optional<CustomOrder> order = orderRepository.findById(orderId);
        if (order.isPresent()) return order.get();
        throw new OrderException("Order not found with the id " + orderId);
    }

    @Override
    public List<CustomOrder> getUserOrders(Long userId) throws OrderException {
        return orderRepository.findAllUserOrders(userId);
    }

    @Override
    public List<CustomOrder> getOrdersOfRestaurant(Long restaurantId, String orderStatus) 
            throws OrderException, RestaurantException {

        List<CustomOrder> orders = orderRepository.findOrdersByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream()
                    .filter(order -> order.getOrderStatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public CustomOrder updateOrder(Long orderId, String orderStatus) throws OrderException {
        CustomOrder order = findOrderById(orderId);

        if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") ||
            orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            Notification notification = notificationService.sendOrderStatusNotification(order);
            return orderRepository.save(order);
        } else {
            throw new OrderException("Please Select A Valid Order Status");
        }
    }
}

