package com.lemorning.demo.customOrder;

import com.lemorning.demo.creditcard.CreditCardInfo;
import com.lemorning.demo.creditcard.CreditCardRepository;
import com.lemorning.demo.customOrderProduct.CustomOrderProduct;
import com.lemorning.demo.customProduct.CustomProduct;
import com.lemorning.demo.customProduct.CustomProductRepository;
import com.lemorning.demo.user.User;
import com.lemorning.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOrderService {

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
        return customOrder.getId();
    }

    @Transactional
    public Long save(CustomOrder customOrder) {
        customOrderRepository.save(customOrder);

        return customOrder.getId();
    }

    public CustomOrder findById(Long id) {
        return customOrderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<CustomOrder> findAll() {
        return customOrderRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        customOrderRepository.deleteById(id);
    }
}
