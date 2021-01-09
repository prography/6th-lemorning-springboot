package com.lemorning.demo.creditcard;

import com.lemorning.demo.user.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CreditCardControllerTest {

    private String email;
    private String password;
    private String auth;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Gender gender;
    private String nickname;
    private String profileImageUrl;

    @Autowired
    CreditCardService creditCardService;

//    @BeforeEach
//    public void initDB(){
//        CreditCardInfo cardInfo1 = CreditCardInfo.builder().creditCardBank(CreditCardBank.국민).birth(LocalDate.of(20,10,12)).build();
//        CreditCardInfo cardInfo2 = CreditCardInfo.builder().creditCardBank(CreditCardBank.SC제일).birth(LocalDate.of(20,10,12)).build();
//        CreditCardInfo cardInfo3 = CreditCardInfo.builder().creditCardBank(CreditCardBank.기업).birth(LocalDate.of(20,10,12)).build();
//        CreditCardInfo cardInfo4 = CreditCardInfo.builder().creditCardBank(CreditCardBank.농협).birth(LocalDate.of(20,10,12)).build();
//        creditCardService.save(cardInfo1);
//        creditCardService.save(cardInfo2);
//        creditCardService.save(cardInfo3);
//        creditCardService.save(cardInfo4);
//    }


    /**
     * 리스트 조회
     */
    @Test
    public void list(){
        List<CreditCardInfo> list = creditCardService.findAll();

        assertThat(list.size()).isEqualTo(4);
    }

}