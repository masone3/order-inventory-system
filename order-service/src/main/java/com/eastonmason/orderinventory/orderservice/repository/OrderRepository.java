package com.eastonmason.orderinventory.orderservice.repository;

import com.eastonmason.orderinventory.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}