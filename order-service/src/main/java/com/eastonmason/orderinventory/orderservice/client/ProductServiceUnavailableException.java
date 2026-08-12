package com.eastonmason.orderinventory.orderservice.client;

public class ProductServiceUnavailableException extends RuntimeException {
    public ProductServiceUnavailableException(Long productId) {
        super("Product service is currently unavailable — could not reserve stock for product " + productId);
    }
}