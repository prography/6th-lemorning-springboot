package com.example.demo.user;

import com.example.demo.creditcard.CreditCardBank;
import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.point.Point;
import com.example.demo.point.PointChargeDto;
import com.example.demo.point.PointService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    EntityManager em;

    @BeforeEach
    public void initDB(){
        User user1 = new User("test1@naver.com",new Point(0));
        User user2 = new User("test2@naver.com",new Point(0));
        userRepository.save(user1);
        userRepository.save(user2);
    }


    @Test
    public void userchargeTest(){
        Point point = pointService.chargePoint(new PointChargeDto(100), 1L);
        userService.updatePointInfo(1L,point.getPointAmount());

        User byEmail = userRepository.findByEmail("test1@naver.com").orElseThrow(EntityNotFoundException::new);
        int pointChargeSum = pointService.amountSum(byEmail.getId());
        Assertions.assertThat(pointChargeSum).isEqualTo(100);
        Assertions.assertThat(byEmail.getPointSum()).isEqualTo(100);
    }
}