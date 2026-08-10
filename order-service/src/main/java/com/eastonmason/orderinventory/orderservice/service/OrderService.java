package com.eastonmason.orderinventory.orderservice.service;

import com.eastonmason.orderinventory.orderservice.client.ProductServiceClient;
import com.eastonmason.orderinventory.orderservice.dto.OrderItemRequest;
import com.eastonmason.orderinventory.orderservice.dto.OrderRequest;
import com.eastonmason.orderinventory.orderservice.dto.OrderResponse;
import com.eastonmason.orderinventory.orderservice.dto.ProductClientResponse;
import com.eastonmason.orderinventory.orderservice.dto.StockReservationRequest;
import com.eastonmason.orderinventory.orderservice.exception.OrderNotFoundException;
import com.eastonmason.orderinventory.orderservice.exception.OrderRejectedException;
import com.eastonmason.orderinventory.orderservice.model.Order;
import com.eastonmason.orderinventory.orderservice.model.OrderItem;
import com.eastonmason.orderinventory.orderservice.model.OrderStatus;
import com.eastonmason.orderinventory.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

    public OrderService(OrderRepository orderRepository, ProductServiceClient productServiceClient) {
        this.orderRepository = orderRepository;
        this.productServiceClient = productServiceClient;
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

        try {
            for (OrderItemRequest itemRequest : request.items()) {
                ProductClientResponse product = productServiceClient.reserveStock(
                        itemRequest.productId(),
                        new StockReservationRequest(itemRequest.quantity())
                );

                OrderItem item = new OrderItem(
                        product.id(),
                        product.name(),
                        itemRequest.quantity(),
                        product.price()
                );
                order.addItem(item);
            }
            order.setStatus(OrderStatus.CONFIRMED);
        } catch (Exception e) {
            order.setStatus(OrderStatus.REJECTED);
            Order saved = orderRepository.save(order);
            throw new OrderRejectedException(
                    "Order rejected: unable to reserve stock — " + e.getMessage()
            );
        }

        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }
}