package com.example.demo.creditcard;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCardInfo, Long> {
    @Query("select c from CreditCardInfo c join fetch c.user u")
    List<CreditCardInfo> findByUser(Long userId);

    Optional<CreditCardInfo> findBycardNum(String cardNum);
}
