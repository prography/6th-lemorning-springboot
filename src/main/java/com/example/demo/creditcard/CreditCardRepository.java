package com.example.demo.creditcard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCardInfo, Long> {
}
