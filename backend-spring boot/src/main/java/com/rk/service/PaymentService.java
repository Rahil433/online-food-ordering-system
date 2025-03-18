package com.rk.service;

import com.rk.model.CustomOrder;
import com.rk.model.PaymentResponse;

public interface PaymentService {

    // Razorpay doesn't throw StripeException, so we simplify this
    public PaymentResponse generatePaymentLink(CustomOrder order);
}
