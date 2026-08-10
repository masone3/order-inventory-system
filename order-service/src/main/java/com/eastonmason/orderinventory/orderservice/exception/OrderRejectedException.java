package com.eastonmason.orderinventory.orderservice.exception;

public class OrderRejectedException extends RuntimeException {
    public OrderRejectedException(String message) {
        super(message);
    }
}