package com.codeline.inventory.service;

import com.codeline.inventory.entity.Category;
import com.codeline.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }
}