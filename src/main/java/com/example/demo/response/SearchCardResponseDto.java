package com.example.demo.response;

import com.example.demo.creditcard.CreditCardBank;
import com.example.demo.creditcard.CreditCardInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
public class SearchCardResponseDto {

    @Enumerated(EnumType.STRING)
    private CreditCardBank creditCardBank;
    private String cardNickName;
    private String cardNum;
    private int expireYear;
    private int expireMonth;
    private LocalDate birth;
    private String simplePassword;

    public SearchCardResponseDto(CreditCardInfo card) {
        this.creditCardBank = card.getCreditCardBank();
        this.cardNickName = card.getCardNickName();
        this.cardNum = card.getCardNum();
        this.expireYear = card.getExpireYear();
        this.expireMonth = card.getExpireMonth();
        this.birth = card.getBirth();
        this.simplePassword = card.getSimplePassword();
    }
}
