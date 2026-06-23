package com.codeline.inventory.repository;

import com.codeline.inventory.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
