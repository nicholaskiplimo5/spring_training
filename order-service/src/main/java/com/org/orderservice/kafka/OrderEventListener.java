package com.org.orderservice.kafka;

import com.org.common.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {
    @KafkaListener(topics = "order-events", groupId = "user-service")
    public void handleOrderEvent(OrderEvent event) {
        System.out.println("Received event: " + event.getUserId());

        // Do something (e.g update user, log, notification)
    }
}
