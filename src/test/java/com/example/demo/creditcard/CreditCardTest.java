package com.example.demo.creditcard;

import com.example.demo.point.PointService;
import com.example.demo.user.JwtUserDetailsService;
import com.example.demo.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class CreditCardTest {

    @Autowired
    private JwtUserDetailsService userService;
    @Autowired
    private PointService pointService;
    @Autowired
    private CreditCardService cardService;
    @Autowired
    private EntityManager em;

    @Test
    public void addCreditCardTest() throws Exception {
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

        User findUser = userService.findByEmail(user.getEmail());
//        CreditCardInfo findCard = cardService.findOne(findUser.getId());
        List<CreditCardInfo> findCards = cardService.findUserCardList(findUser.getId());

        System.out.println("finduser = " + findUser.toString());

        for (CreditCardInfo findCard : findCards)
            System.out.println("--> findCard = " + findCard.toString());
    }
}
