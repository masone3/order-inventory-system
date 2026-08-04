package com.eastonmason.orderinventory.orderservice.dto;

import com.eastonmason.orderinventory.orderservice.model.Order;

import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        String customerName,
        String status,
        Instant createdAt,
        Double totalAmount,
        List<OrderItemResponse> items
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getStatus().name(),
                order.getCreatedAt(),
                order.getTotalAmount(),
                order.getItems().stream().map(OrderItemResponse::from).toList()
        );
    }
}