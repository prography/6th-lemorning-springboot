package com.example.demo.order;

import com.example.demo.creditcard.CreditCardBank;
import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.creditcard.CreditCardService;
import com.example.demo.exception.NotEnoughPointException;
import com.example.demo.point.PointService;
import com.example.demo.product.CustomProduct;
import com.example.demo.product.GeneralProduct;
import com.example.demo.product.ProductService;
import com.example.demo.user.JwtUserDetailsService;
import com.example.demo.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class OrderTest {

    @Autowired
    private JwtUserDetailsService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PointService pointService;
    @Autowired
    private CreditCardService cardService;
    @Autowired
    private ProductService productService;

    @Test
    public void orderTest() throws Exception {
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

        // 포인트 충전
        User findUser = userService.findByEmail(user.getEmail());
        List<CreditCardInfo> cardList = cardService.findUserCardList(findUser.getId());

        cardService.chargePoint(findUser.getEmail(), cardList.get(0).getCardNum(), 10000);
        cardService.chargePoint(findUser.getEmail(), cardList.get(1).getCardNum(), 30000);
        cardService.chargePoint(findUser.getEmail(), cardList.get(0).getCardNum(), 50000);

        System.out.println("주문 전 현재 포인트 : " + userService.findByEmail(user.getEmail()).getPoint());

        // 주문 상품 생성
        GeneralProduct gp = new GeneralProduct();
        gp.setAuthor("gp1");
        gp.setCategoryName("General");
        gp.setPrice(10000);
        gp.setProductName("gpgpgpgpgpgpg");
        gp.setImageUrl("Wwwwwwwwwww");
        productService.save(gp);

        CustomProduct cp = new CustomProduct();
        cp.setAuthor("cp1");
        cp.setCategoryName("Custom");
        cp.setPrice(80000);
        cp.setProductName("cpcpcpccpcppccsp");
        cp.setAlarmUrl("ffffffffff");
        productService.save(cp);

        orderService.order(findUser.getEmail(), gp.getId(), cp.getId());
        System.out.println("주문 후 현재 포인트 : " + userService.findByEmail(user.getEmail()).getPoint());

        CustomProduct cp2 = new CustomProduct();
        cp2.setAuthor("cp2");
        cp2.setCategoryName("Custom");
        cp2.setPrice(20000);
        cp2.setProductName("vnznb,xncblanksdj");
        cp2.setAlarmUrl("lcmvmmvmbmcm");
        productService.save(cp2);

        try {
            orderService.order(findUser.getEmail(), cp2.getId());
        } catch (NotEnoughPointException e) {
            return;
        }

        fail("포인트 초과가 발생해야 한다.");
    }
}