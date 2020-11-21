package com.example.demo.request;

import com.example.demo.creditcard.CreditCardBank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
public class CreateCardRequestDto {
    private String email;
    @Enumerated(EnumType.STRING)
    private CreditCardBank creditCardBank;
    private String cardNickName;
    private String cardNum;
    private int expireYear;
    private int expireMonth;
    private String simplePassword;

    public CreateCardRequestDto(String email, CreditCardBank creditCardBank, String cardNickName, String cardNum, int expireYear, int expireMonth, String simplePassword) {
        this.email = email;
        this.creditCardBank = creditCardBank;
        this.cardNickName = cardNickName;
        this.cardNum = cardNum;
        this.expireYear = expireYear;
        this.expireMonth = expireMonth;
        this.simplePassword = simplePassword;
    }
}
