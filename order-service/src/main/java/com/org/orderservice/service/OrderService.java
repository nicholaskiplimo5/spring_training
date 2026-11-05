package com.org.orderservice.service;
import com.org.common.dto.OrderEvent;
import com.org.orderservice.kafka.OrderEventProducer;
import com.org.orderservice.model.Order;
import com.org.orderservice.model.OrderStatus;
import com.org.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository repository, OrderEventProducer orderEventProducer) {
        this.repository = repository;
        this.orderEventProducer = orderEventProducer;
    }

    public Order createOrder(Order order) {

        order.setStatus(OrderStatus.PENDING);
        Order saved = repository.save(order);

        // 🔥 Send event instead of calling User Service

        OrderEvent event= new OrderEvent(
                saved.getId(),
                saved.getUserId(),
                saved.getProduct()
        );

        orderEventProducer.sendOrderCreatedEvent(event);

        return saved;
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }
}
