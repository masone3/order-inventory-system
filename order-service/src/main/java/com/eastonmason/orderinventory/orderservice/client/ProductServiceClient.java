package com.eastonmason.orderinventory.orderservice.client;

import com.eastonmason.orderinventory.orderservice.dto.ProductClientResponse;
import com.eastonmason.orderinventory.orderservice.dto.StockReservationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @PostMapping("/products/{id}/reserve")
    ProductClientResponse reserveStock(@PathVariable("id") Long id, @RequestBody StockReservationRequest request);
}