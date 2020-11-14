package com.example.demo.order;

import com.example.demo.orderProduct.OrderProduct;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
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
    private final ProductService itemService;

    // 주문
    @Transactional
    public Long order(String email, Long itemId){

        // 엔티티 조회
        User member = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);     // 영속 상태에서 제어 가능하다.
        Product product = itemService.findById(itemId);

        // 주문 상품 생성
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product);

        // 주문 생성 + 포인트 차감.
        Order order = Order.createOrder(member, orderProduct);

        // 주문 저장
        orderRepository.save(order);    // cascade.all 때문에 퍼진다.
        return order.getId();
    }

    @Transactional
    public Order findById(Long orderedId) {
        return orderRepository.findById(orderedId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public List<OrderProduct> findAllByUser(User user){
        List<Order> orders = orderRepository.findAllByUser(user);
        List<OrderProduct> answer = new ArrayList<>();
        for(Order order: orders){
            answer.addAll(order.getOrderProducts());
        }
        return answer;
    }
}
