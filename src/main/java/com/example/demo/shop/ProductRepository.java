package com.example.demo.shop;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Product findByName(String name);

  boolean existsByName(String name);
}