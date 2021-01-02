package com.example.demo.point;

import com.example.demo.creditcard.CreditCardBank;
import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.creditcard.CreditCardService;
import com.example.demo.user.JwtUserDetailsService;
import com.example.demo.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
//@Rollback(value = false)
class PointTest {

    @Autowired
    private PointService pointService;
    @Autowired
    private JwtUserDetailsService userService;
    @Autowired
    private CreditCardService cardService;

    @Test
    public void pointChargeTest() throws Exception {
        // 회원 가입
        User user = User.builder()
                .email("ssssssss@naver.com")
                .birthday(LocalDate.of(2020, 12, 20))
                .build();
        userService.save(user);

        // 카드 등록
        CreditCardInfo card1 = CreditCardInfo.builder()
                .user(user)
                .cardNickname("testcard1")
                .creditCardBank(CreditCardBank.국민)
                .expireYear(9999)
                .expireMonth(12)
                .cardNum("12345535353")
                .build();

        CreditCardInfo card2 = CreditCardInfo.builder()
                .user(user)
                .cardNickname("testcard2")
                .creditCardBank(CreditCardBank.농협)
                .expireYear(10000000)
                .expireMonth(30)
                .cardNum("565456")
                .build();

        cardService.save(card1);
        cardService.save(card2);

        // when
        User findUser = userService.findByEmail(user.getEmail());
        List<CreditCardInfo> cardList = cardService.findUserCardList(findUser.getId());

        cardService.chargePoint(findUser.getEmail(), cardList.get(0).getCardNum(), 10000);
        cardService.chargePoint(findUser.getEmail(), cardList.get(1).getCardNum(), 30000);

        List<Point> all = pointService.findAll();

        for (Point point : all)
            System.out.println("point = " + point.toString());

        Assertions.assertThat(pointService.getTotalPointByCard(cardList.get(0).getCardNum())).isEqualTo(10000);
        Assertions.assertThat(pointService.getTotalPointByCard(cardList.get(1).getCardNum())).isEqualTo(30000);
        Assertions.assertThat(pointService.getTotalPointByUser(findUser.getEmail())).isEqualTo(40000);
    }
}