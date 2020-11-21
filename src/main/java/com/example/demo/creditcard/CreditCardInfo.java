package com.example.demo.creditcard;

import com.example.demo.request.UpdateCardRequestDto;
import com.example.demo.point.Point;
import com.example.demo.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"creditCardBank", "cardNickName", "expireYear", "expireMonth", "birth", "simplePassword"})
public class CreditCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_card_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CreditCardBank creditCardBank;

    private String cardNickName;

    private String cardNum;

    private int expireYear;

    private int expireMonth;

    private LocalDate birth;

    private String simplePassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
    private List<Point> pointHistory = new ArrayList<>();

    @Builder
    public CreditCardInfo(User user,
                          CreditCardBank creditCardBank,
                          String cardNickname,
                          String cardNum,
                          int expireYear,
                          int expireMonth,
                          String simplePassword) {
        this.creditCardBank = creditCardBank;
        this.cardNickName = cardNickname;
        this.cardNum = cardNum;
        this.expireYear = expireYear;
        this.expireMonth = expireMonth;
        this.simplePassword = simplePassword;
        addUser(user);
    }

    // CreditCardInfo - User 연관관계 메소드
    public void addUser(User user) {
        this.user = user;
        this.birth = user.getBirthday();
        user.getCreditCardList().add(this);
    }

    public void updateCreditCard(UpdateCardRequestDto card) {
        this.cardNickName = card.getCardNickName();
        this.simplePassword = card.getSimplePassword();
    }
}
