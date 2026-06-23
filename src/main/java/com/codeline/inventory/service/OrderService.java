package com.codeline.inventory.service;

import com.codeline.inventory.entity.*;
import com.codeline.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public Order createOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.DRAFT);
        return orderRepository.save(order);
    }

    public Order addItem(Long orderId, Long productId, Integer quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.DRAFT)
            throw new RuntimeException("Order is not in DRAFT status");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPriceAtTime(product.getPrice());
        order.getItems().add(item);
        return orderRepository.save(order);
    }

    public Order removeItem(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.DRAFT)
            throw new RuntimeException("Order is not in DRAFT status");
        order.getItems().removeIf(i -> i.getId().equals(itemId));
        return orderRepository.save(order);
    }

    @Transactional
    public Order confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.DRAFT)
            throw new RuntimeException("Order is not in DRAFT status");
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (product.getStockQuantity() < item.getQuantity())
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }
        double total = order.getItems().stream()
                .mapToDouble(i -> i.getPriceAtTime() * i.getQuantity())
                .sum();
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (newStatus == OrderStatus.CANCELLED && order.getStatus() == OrderStatus.CONFIRMED) {
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}