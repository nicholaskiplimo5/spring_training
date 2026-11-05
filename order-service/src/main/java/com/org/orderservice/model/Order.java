package com.org.orderservice.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;
    private int quantity;

    private Long userId; // IMPORTANT (links to user-service)

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
