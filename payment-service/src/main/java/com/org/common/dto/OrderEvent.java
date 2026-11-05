package com.org.common.dto;
import lombok.Data;

@Data
public class OrderEvent {

    private Long orderId;
    private Long userId;
    private String status;
}
