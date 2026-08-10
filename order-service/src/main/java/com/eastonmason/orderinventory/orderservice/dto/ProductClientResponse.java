package com.eastonmason.orderinventory.orderservice.dto;

public record ProductClientResponse(
        Long id,
        String name,
        String sku,
        Integer stockQuantity,
        Double price
) {}