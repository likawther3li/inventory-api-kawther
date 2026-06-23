package com.codeline.inventory.service;

import com.codeline.inventory.entity.Category;
import com.codeline.inventory.entity.Product;
import com.codeline.inventory.repository.CategoryRepository;
import com.codeline.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product create(String name, Double price, Integer stock, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStockQuantity(stock);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product adjustStock(Long productId, Integer amount) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStockQuantity(product.getStockQuantity() + amount);
        return productRepository.save(product);
    }

    public List<Product> getLowStock(Integer threshold) {
        return productRepository.findByStockQuantityLessThan(threshold);
    }
}