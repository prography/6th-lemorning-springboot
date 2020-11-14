package com.example.demo.customOrder;

<<<<<<< HEAD
import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.creditcard.CreditCardRepository;
import com.example.demo.customOrderProduct.CustomOrderProduct;
import com.example.demo.customProduct.CustomProduct;
import com.example.demo.customProduct.CustomProductRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
=======
>>>>>>> add Service & Repository
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> add Service & Repository

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOrderService {

<<<<<<< HEAD
    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;
    private final CustomOrderRepository customOrderRepository;
    private final CustomProductRepository customProductRepository;

    @Transactional
    public Long order(Long userId, Long cardId, Long customProductId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        CreditCardInfo card = creditCardRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);
        CustomProduct customProduct = customProductRepository.findById(customProductId).orElseThrow(EntityNotFoundException::new);

        // 주문 상품 생성
        CustomOrderProduct customOrderProduct = CustomOrderProduct.createCustomOrderProduct(customProduct, customProduct.getPrice());

        // 주문
        CustomOrder customOrder = CustomOrder.createCustomOrder(user, card, customOrderProduct);

=======
    private final CustomOrderRepository customOrderRepository;

    @Transactional
    public Long save(CustomOrder customOrder) {
>>>>>>> add Service & Repository
        customOrderRepository.save(customOrder);

        return customOrder.getId();
    }

    public CustomOrder findById(Long id) {
        return customOrderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<CustomOrder> findAll() {
        return customOrderRepository.findAll();
    }
<<<<<<< HEAD

    @Transactional
    public void delete(Long id) {
        customOrderRepository.deleteById(id);
    }
=======
>>>>>>> add Service & Repository
}
