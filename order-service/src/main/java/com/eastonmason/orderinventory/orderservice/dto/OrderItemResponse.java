package com.eastonmason.orderinventory.orderservice.dto;

import com.eastonmason.orderinventory.orderservice.model.OrderItem;

public record OrderItemResponse(
        Long productId,
        String productName,
        Integer quantity,
        Double unitPrice,
        Double lineTotal
) {
    public static OrderItemResponse from(OrderItem item) {
        return new OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getLineTotal()
        );
    }
}