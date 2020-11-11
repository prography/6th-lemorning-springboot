package com.example.demo.customProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomProductRepository extends JpaRepository<CustomProduct, Long> {
}
