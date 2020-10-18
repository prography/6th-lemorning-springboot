package com.example.demo.creditcard;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creditcard_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CreditCardBank creditCardBank;

    private String cardNickname;

    private String cardNum;

    private int expireYear;

    private int expireMonth;

    private LocalDate birth;

    private String simplePassword;

    @Builder
    public CreditCardInfo(CreditCardBank creditCardBank, String cardNickname, String cardNum, int expireYear, int expireMonth, String birth, String simplePassword) {
        this.creditCardBank = creditCardBank;
        this.cardNickname = cardNickname;
        this.cardNum = cardNum;
        this.expireYear = expireYear;
        this.expireMonth = expireMonth;
        this.birth = getBirthDate(birth);
        this.simplePassword = simplePassword;
    }

    public LocalDate getBirthDate(String birth) {
        int year = Integer.parseInt(birth.substring(0, 3));
        int month = Integer.parseInt(birth.substring(5, 6));
        int day = Integer.parseInt(birth.substring(8, 9));

        return LocalDate.of(year, month, day);
    }

    public void update(CreditCardInfo creditCardInfo) {
        this.cardNickname = creditCardInfo.cardNickname;
        this.cardNum = creditCardInfo.cardNum;
        this.expireYear = creditCardInfo.expireYear;
        this.expireMonth = creditCardInfo.expireMonth;
        this.simplePassword = creditCardInfo.simplePassword;
    }
}
