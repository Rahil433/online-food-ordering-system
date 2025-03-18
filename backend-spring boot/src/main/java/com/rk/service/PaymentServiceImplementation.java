package com.rk.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.rk.model.CustomOrder;
import com.rk.model.PaymentResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImplementation implements PaymentService {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    public PaymentResponse generatePaymentLink(CustomOrder order) {  // ✅ Updated to use CustomOrder

        PaymentResponse response = new PaymentResponse();

        try {
            // Initialize Razorpay client
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            // Create Razorpay order request
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", order.getTotalAmount() * 100);  // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_" + order.getId());

            JSONObject notes = new JSONObject();
            notes.put("orderId", String.valueOf(order.getId()));
            notes.put("product", "pizza burger");  // Optional
            orderRequest.put("notes", notes);

            Order razorpayOrder = razorpayClient.orders.create(orderRequest);

            response.setPayment_url("https://zosh-food.vercel.app/payment/success/" + order.getId());
            response.setOrderId(razorpayOrder.get("id"));
            response.setCurrency(razorpayOrder.get("currency"));
            response.setAmount(razorpayOrder.get("amount"));

            return response;

        } catch (RazorpayException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating Razorpay order: " + e.getMessage());
        }
    }
}

