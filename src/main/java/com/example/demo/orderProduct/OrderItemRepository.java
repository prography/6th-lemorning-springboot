package com.example.demo.orderProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderProduct,Long> {
}
