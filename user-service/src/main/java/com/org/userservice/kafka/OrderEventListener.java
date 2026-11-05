package com.org.userservice.kafka;

import com.org.common.dto.OrderEvent;
import com.org.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-events", groupId = "user-service")
    public void handleOrderEvent(OrderEvent event) {

        System.out.println("Received: " + event);

        if (event.getUserId() == null) {
            throw new RuntimeException("Invalid user!");
        }

        try {
            log.info("Received order event: orderId={}, userId={}",
                    event.getOrderId(), event.getUserId());

            boolean userExists = userService.existsById(event.getUserId());

            if (userExists) {
                log.info("User exists → sending payment request");
                // Send to payment service
                kafkaTemplate.send("payment-requested", event);
            } else {
                log.warn("User NOT found → cancelling order");
                // Cancel order directly
                kafkaTemplate.send("order-cancelled", event);
            }
        } catch (Exception e) {
            log.error("Error processing order event, will retry", e);

            // do NOT acknowledge → Kafka will retry
            throw e;
        }
    }

    @KafkaListener(topics = "order-events.DLT", groupId = "user-service-dlt")
    public void handleDLQ(OrderEvent event) {

        System.out.println("💀 DLQ EVENT: " + event);

        // You can:
        // - log
        // - save to DB
        // - trigger alert
    }
}