package com.org.paymentservice.service;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    public boolean processPayment(Long orderId) {
        System.out.println("Processing payment for order: " + orderId);
        // simulate real payment logic
        return new Random().nextBoolean();
    }
}