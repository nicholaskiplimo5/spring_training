package com.org.common.dto;

public class OrderEvent {
    private Long orderId;
    private Long userId;
    private String product;

    public OrderEvent() {}

    public OrderEvent(Long orderId, Long userId, String product) {
        this.orderId = orderId;
        this.userId = userId;
        this.product = product;
    }

    public Long getOrderId() { return orderId; }
    public Long getUserId() { return userId; }
    public String getProduct() { return product; }

    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setProduct(String product) { this.product = product; }
}
