package com.org.orderservice.kafka;

import com.org.common.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderEvent event) {
        kafkaTemplate.send("order-events", event);
    }
}
