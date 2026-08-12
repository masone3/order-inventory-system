package com.eastonmason.orderinventory.orderservice.client;

import com.eastonmason.orderinventory.orderservice.dto.ProductClientResponse;
import com.eastonmason.orderinventory.orderservice.dto.StockReservationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", fallback = ProductServiceClient.ProductServiceClientFallback.class)
public interface ProductServiceClient {

    @PostMapping("/products/{id}/reserve")
    ProductClientResponse reserveStock(@PathVariable("id") Long id, @RequestBody StockReservationRequest request);

    @Component
    class ProductServiceClientFallback implements ProductServiceClient {
        @Override
        public ProductClientResponse reserveStock(Long id, StockReservationRequest request) {
            throw new ProductServiceUnavailableException(id);
        }
    }
}