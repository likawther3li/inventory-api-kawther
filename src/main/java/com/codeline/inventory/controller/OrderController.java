package com.codeline.inventory.controller;

import com.codeline.inventory.entity.Order;
import com.codeline.inventory.entity.OrderStatus;
import com.codeline.inventory.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Long> body) {
        return ResponseEntity.status(201).body(orderService.createOrder(body.get("customerId")));
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<Order> addItem(@PathVariable Long orderId,
                                         @RequestBody Map<String, Object> body) {
        Long productId = Long.valueOf(body.get("productId").toString());
        Integer quantity = Integer.valueOf(body.get("quantity").toString());
        return ResponseEntity.ok(orderService.addItem(orderId, productId, quantity));
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Order> removeItem(@PathVariable Long orderId,
                                            @PathVariable Long itemId) {
        return ResponseEntity.ok(orderService.removeItem(orderId, itemId));
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<Order> confirm(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.confirmOrder(orderId));
    }

    @PostMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long orderId,
                                              @RequestBody Map<String, String> body) {
        OrderStatus status = OrderStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}