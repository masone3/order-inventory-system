package com.eastonmason.orderinventory.orderservice.service;

import com.eastonmason.orderinventory.orderservice.client.ProductServiceClient;
import com.eastonmason.orderinventory.orderservice.client.ProductServiceUnavailableException;
import com.eastonmason.orderinventory.orderservice.dto.ProductClientResponse;
import com.eastonmason.orderinventory.orderservice.dto.StockReservationRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class ProductReservationService {

    private final ProductServiceClient productServiceClient;

    public ProductReservationService(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "reserveStockFallback")
    @Retry(name = "productService")
    public ProductClientResponse reserveStock(Long productId, StockReservationRequest request) {
        return productServiceClient.reserveStock(productId, request);
    }

    public ProductClientResponse reserveStockFallback(Long productId, StockReservationRequest request, Throwable t) {
        throw new ProductServiceUnavailableException(productId);
    }
}