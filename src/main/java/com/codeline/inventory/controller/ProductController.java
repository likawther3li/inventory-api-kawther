package com.codeline.inventory.controller;

import com.codeline.inventory.entity.Product;
import com.codeline.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        Double price = Double.valueOf(body.get("price").toString());
        Integer stock = Integer.valueOf(body.get("stockQuantity").toString());
        Long categoryId = Long.valueOf(body.get("categoryId").toString());
        return ResponseEntity.status(201).body(
                productService.create(name, price, stock, categoryId)
        );
    }

    @PostMapping("/{productId}/stock-adjustment")
    public ResponseEntity<Product> adjustStock(@PathVariable Long productId,
                                               @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(productService.adjustStock(productId, body.get("amount")));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> lowStock(@RequestParam Integer threshold) {
        return ResponseEntity.ok(productService.getLowStock(threshold));
    }
}
