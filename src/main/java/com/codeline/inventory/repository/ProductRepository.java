package com.codeline.inventory.repository;

import com.codeline.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockQuantityLessThan(Integer threshold);
}
