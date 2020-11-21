package com.example.demo.order;

import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.creditcard.CreditCardRepository;
import com.example.demo.orderProduct.OrderProduct;
import com.example.demo.point.Point;
import com.example.demo.point.PointRepository;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // 주문
    @Transactional
    public Long order(String email, Long... productIds) {
        // 엔티티 조회
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);     // 영속 상태에서 제어 가능하다.

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product);

            orderProducts.add(orderProduct);
        }

        // 주문 생성 + 포인트 차감.
        Order order = Order.createOrder(user, orderProducts);

        // 주문 저장
        orderRepository.save(order);    // cascade.all 때문에 퍼진다.

        return order.getId();
    }

    public Order findById(Long orderedId) {
        return orderRepository.findById(orderedId).orElseThrow(EntityNotFoundException::new);
    }
}
