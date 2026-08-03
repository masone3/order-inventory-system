package com.eastonmason.orderinventory.productservice.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId, Integer requested, Integer available) {
        super("Insufficient stock for product " + productId +
                ": requested " + requested + ", available " + available);
    }
}