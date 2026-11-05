package com.org.orderservice.dto;

import com.org.orderservice.model.OrderStatus;
import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private OrderStatus status;
}
