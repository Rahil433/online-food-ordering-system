package com.rk.model;

import lombok.Data;

@Data
public class PaymentResponse {

    private String orderId;
    private String currency;
    private int amount;
    private String payment_url;  // Keep this if you want to use it

}
