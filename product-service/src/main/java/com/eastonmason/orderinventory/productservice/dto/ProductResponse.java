package com.eastonmason.orderinventory.productservice.dto;

import com.eastonmason.orderinventory.productservice.model.Product;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        Integer stockQuantity,
        Double price
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getStockQuantity(),
                product.getPrice()
        );
    }
}
