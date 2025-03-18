package com.rk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rk.model.CustomOrder;
import com.rk.model.PaymentResponse;
import com.rk.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
//	
//	@PostMapping("/payment")
//	public ResponseEntity<PaymentResponse> generatePaymentLink(@RequestBody Order order) {
//		
//		PaymentResponse res = paymentService.generatePaymentLink(order);
//		
//		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//	}

}
