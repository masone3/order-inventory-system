package com.eastonmason.orderinventory.orderservice.service;

import com.eastonmason.orderinventory.orderservice.dto.OrderItemRequest;
import com.eastonmason.orderinventory.orderservice.dto.OrderRequest;
import com.eastonmason.orderinventory.orderservice.dto.OrderResponse;
import com.eastonmason.orderinventory.orderservice.exception.OrderNotFoundException;
import com.eastonmason.orderinventory.orderservice.model.Order;
import com.eastonmason.orderinventory.orderservice.model.OrderItem;
import com.eastonmason.orderinventory.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderResponse.from(order);
    }

    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order(request.customerName());

        for (OrderItemRequest itemRequest : request.items()) {
            ProductStub product = lookupProductStub(itemRequest.productId());

            OrderItem item = new OrderItem(
                    product.id(),
                    product.name(),
                    itemRequest.quantity(),
                    product.price()
            );
            order.addItem(item);
        }

        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }

    // TEMPORARY STUB — replaced on Day 5 with a real Feign call to product-service
    private ProductStub lookupProductStub(Long productId) {
        return new ProductStub(productId, "Stub Product #" + productId, 19.99);
    }

    private record ProductStub(Long id, String name, Double price) {}
}