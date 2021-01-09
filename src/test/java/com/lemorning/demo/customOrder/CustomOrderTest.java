package com.lemorning.demo.customOrder;

import com.lemorning.demo.creditcard.CreditCardBank;
import com.lemorning.demo.creditcard.CreditCardInfo;
import com.lemorning.demo.creditcard.CreditCardRepository;
import com.lemorning.demo.customProduct.CustomProduct;
import com.lemorning.demo.customProduct.CustomProductRepository;
import com.lemorning.demo.user.Gender;
import com.lemorning.demo.user.User;
import com.lemorning.demo.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class CustomOrderTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CustomProductRepository customProductRepository;

    @Autowired
    private CustomOrderRepository customOrderRepository;

    @Autowired
    private CustomOrderService customOrderService;

    @Test
    public void customOrder() throws Exception {
        // given
        User user = createUser("swj@naver.com", "oneone", LocalDate.of(2020, 10, 10), Gender.MALE, "1234");

        CreditCardInfo creditCardInfo1 = createCard(user, "one card", CreditCardBank.국민, 1000, 10, "1111");
        CreditCardInfo creditCardInfo2 = createCard(user, "two card", CreditCardBank.농협, 2000, 20, "2222");

        User user2 = createUser("test@gmail.com", "twotwo", LocalDate.of(2020, 11, 14), Gender.FEMALE, "7765");
        CreditCardInfo creditCardInfo3 = createCard(user2, "etc", CreditCardBank.카카오뱅크, 3000, 30, "33343");

        CustomProduct cp1 = createCustomProduct(10000, "wonju", "Test");
        CustomProduct cp2 = createCustomProduct(404004040, "ddddddd", "jskdfjksjfdkda");
        CustomProduct cp3 = createCustomProduct(999999, "function", "jjjjjj");

        // when
        Long customOrderId = customOrderService.order(user.getId(), creditCardInfo1.getId(), cp1.getId());

        CustomOrder customOrder = customOrderRepository.findById(customOrderId).orElseThrow(EntityExistsException::new);

        // then
        assertThat(customOrder.getUser().getId()).isEqualTo(1L);
        assertThat(customOrder.getCreditCardInfo().getCardNickname()).isEqualTo("one card");
    }

    private CustomProduct createCustomProduct(int price, String author, String productName) {
        CustomProduct cp1 = new CustomProduct();
        cp1.setPrice(price);
        cp1.setAuthor(author);
        cp1.setProductName(productName);

        customProductRepository.save(cp1);

        return cp1;
    }

    private CreditCardInfo createCard(User user, String nickname, CreditCardBank bank, int year, int month, String password) {
        CreditCardInfo c = CreditCardInfo.builder()
                .cardNickname(nickname)
                .creditCardBank(bank)
                .expireYear(year)
                .expireMonth(month)
                .simplePassword(password)
                .birth(user.getBirthday())
                .build();

        user.addCreditCardInfo(c);
        creditCardRepository.save(c);

        return c;
    }

    private User createUser(String email, String nickname, LocalDate birth, Gender gender, String password) {
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .birthday(birth)
                .gender(gender)
                .password(password)
                .build();

        userRepository.save(user);

        return user;
    }
}