package com.codeline.inventory.controller;

import com.codeline.inventory.entity.Category;
import com.codeline.inventory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(201).body(categoryService.create(body.get("name")));
    }
}