package com.codeline.inventory.controller;

import com.codeline.inventory.entity.Customer;
import com.codeline.inventory.entity.Order;
import com.codeline.inventory.service.CustomerService;
import com.codeline.inventory.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(201).body(
                customerService.create(body.get("name"), body.get("email"))
        );
    }

    @PostMapping("/{customerId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Long customerId) {
        return ResponseEntity.status(201).body(orderService.createOrder(customerId));
    }
}