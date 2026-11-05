package com.org.paymentservice.kafka;

import com.org.common.dto.OrderEvent;
import com.org.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "payment-requested", groupId = "payment-service")
    public void handlePayment(OrderEvent event) {

        try {
            System.out.println("Received payment request: " + event.getOrderId());

            boolean success = paymentService.processPayment(event.getOrderId());

            if (success) {
                event.setStatus("CONFIRMED");
                kafkaTemplate.send("order-confirmed", event);
                System.out.println("Payment SUCCESS");
            } else {
                event.setStatus("CANCELLED");
                kafkaTemplate.send("order-cancelled", event);
                System.out.println("Payment FAILED");
            }
        } catch (Exception e) {
            System.out.println("Payment processing failed, retrying...");
            throw e; // Kafka retries
        }
    }
}