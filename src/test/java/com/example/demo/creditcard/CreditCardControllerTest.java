package com.example.demo.creditcard;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditCardControllerTest {


    @Autowired
    CreditCardService creditCardService;

    @BeforeEach
    public void initDB(){
        CreditCardInfo cardInfo1 = CreditCardInfo.builder().creditCardBank(CreditCardBank.국민).birth(LocalDate.of(20,10,12).toString()).build();
        CreditCardInfo cardInfo2 = CreditCardInfo.builder().creditCardBank(CreditCardBank.SC제일).birth(LocalDate.of(20,10,12).toString()).build();
        CreditCardInfo cardInfo3 = CreditCardInfo.builder().creditCardBank(CreditCardBank.기업).birth(LocalDate.of(20,10,12).toString()).build();
        CreditCardInfo cardInfo4 = CreditCardInfo.builder().creditCardBank(CreditCardBank.농협).birth(LocalDate.of(20,10,12).toString()).build();
        creditCardService.save(cardInfo1);
        creditCardService.save(cardInfo2);
        creditCardService.save(cardInfo3);
        creditCardService.save(cardInfo4);
    }


    /**
     * 리스트 조회
     */
    @Test
    public void list(){
        List<CreditCardInfo> list = creditCardService.findAll();

        assertThat(list.size()).isEqualTo(4);
    }

}