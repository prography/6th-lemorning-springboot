package com.lemorning.demo.creditcard;

import com.lemorning.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCardInfo, Long> {
    List<CreditCardInfo> findByUser(User user);

    CreditCardInfo findById(long id);
}
