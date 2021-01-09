package com.lemorning.demo.user;

import com.lemorning.demo.order.OrderService;
import com.lemorning.demo.point.PointService;
import com.lemorning.demo.product.Product;
import com.lemorning.demo.product.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUserDetailsService userService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void initDB(){
//        User user1 = new User("test1@naver.com",new Point(0));
//        User user2 = new User("test2@naver.com",new Point(0));
//        User user3 = new User("test3@naver.com",new Point(1000));
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);

        Product product1 = new Product("상품1번",10);
        Product product2 = new Product("상품2번",20);
        Product product3 = new Product("상품3번",30);
        productService.save(product1);
        productService.save(product2);
        productService.save(product3);
    }

    /**
     * 충전 금액이 있다면, 결제가 잘 되는지 확인해보자.
     * 2. 포인트가 있다면 포인트로 결제가 진행이 된다. ( 완료 )
     */
    @Test
    public void purchaseTest(){
        User byEmail = userRepository.findByEmail("test3@naver.com").orElseThrow(EntityNotFoundException::new);
        orderService.order(byEmail.getEmail(), 1L);
        orderService.order(byEmail.getEmail(), 3L);

        Assertions.assertThat(byEmail.getPointSum()).isEqualTo(960);
    }

    /**
     * 충전이 되는지, 충전한 이력이 남는지 테스트해보자.
     * 5. 리턴값을 받아 자체적으로 실제 돈를 올려준다. ( 완료 )
     */
    @Test
    public void userchargeTest(){
//        Point point = pointService.chargePoint(new PointChargeDto(100), 1L);
//        userService.updatePointInfo(1L,point.getPointAmount());
//
//        User byEmail = userRepository.findByEmail("test1@naver.com").orElseThrow(EntityNotFoundException::new);
//        int pointChargeSum = pointService.amountSum(byEmail.getId());
//        Assertions.assertThat(pointChargeSum).isEqualTo(100);
//        Assertions.assertThat(byEmail.getPointSum()).isEqualTo(100);
    }
}