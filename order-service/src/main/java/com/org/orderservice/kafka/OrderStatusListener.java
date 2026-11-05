package com.org.orderservice.kafka;

import com.org.common.dto.OrderEvent;
import com.org.orderservice.model.Order;
import com.org.orderservice.model.OrderStatus;
import com.org.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderStatusListener {
    private final OrderRepository repository;

    public OrderStatusListener(OrderRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "order-confirmed", groupId = "order-service")
    public void confirmOrder(OrderEvent event) {
        Order order = repository.findById(event.getOrderId()).orElseThrow();
        order.setStatus(OrderStatus.CONFIRMED);
        log.info("Order Confirmed");
        repository.save(order);
    }

    @KafkaListener(topics = "order-cancelled", groupId = "order-service")
    public void cancelOrder(OrderEvent event) {
        Order order = repository.findById(event.getOrderId()).orElseThrow();
        order.setStatus(OrderStatus.CANCELLED);
        log.info("Order Cancelled");
        repository.save(order);
    }
}
