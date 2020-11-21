package com.example.demo.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query("select sum(p.point) from Point p where p.id = :credit_card_id")
    int getTotalPointByCreditCard(@Param("credit_card_id") Long cardId);
}
