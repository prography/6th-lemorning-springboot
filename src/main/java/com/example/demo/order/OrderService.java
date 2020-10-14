package com.example.demo.order;

import com.example.demo.orderItem.OrderItem;
import com.example.demo.shop.Product;
import com.example.demo.shop.ProductService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductService itemService;

    // 주문
    @Transactional
    public Long order(String email, Long itemId){

        // 엔티티 조회
        User member = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);     // 영속 상태에서 제어 가능하다.
        Product product = itemService.findById(itemId);

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(product);

        // 주문 생성
        Order order = Order.createOrder(member, orderItem);

        // 주문 저장
        orderRepository.save(order);    // cascade.all 때문에 퍼진다.
        return order.getId();
    }

    @Transactional
    public Order findById(Long orderedId) {
        return orderRepository.findById(orderedId).orElseThrow(EntityNotFoundException::new);
    }
}