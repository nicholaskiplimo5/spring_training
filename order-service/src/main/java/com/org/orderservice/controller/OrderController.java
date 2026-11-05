package com.org.orderservice.controller;
import com.org.orderservice.model.Order;
import com.org.orderservice.repository.OrderRepository;
import com.org.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    private final OrderRepository repository;

    public OrderController(OrderService service, OrderRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return service.createOrder(order);
    }

    @GetMapping
    public List<Order> getOrders() {
        return service.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
