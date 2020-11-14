package com.example.demo.creditcard;

import com.example.demo.customOrder.CustomOrder;
import com.example.demo.order.Order;
import com.example.demo.user.User;
import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_card_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CreditCardBank creditCardBank;

    private String cardNickname;

    private String cardNum;

    private int expireYear;

    private int expireMonth;

    private LocalDate birth;

    private String simplePassword;

//    private String billingKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "creditCardInfo", cascade = CascadeType.ALL)
    private List<CustomOrder> customOrders = new ArrayList<>();

    @OneToMany(mappedBy = "creditCardInfo", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public CreditCardInfo(CreditCardBank creditCardBank, String cardNickname, String cardNum, int expireYear, int expireMonth, LocalDate birth, String simplePassword) {
        this.creditCardBank = creditCardBank;
        this.cardNickname = cardNickname;
        this.cardNum = cardNum;
        this.expireYear = expireYear;
        this.expireMonth = expireMonth;
        this.birth = birth;
        this.simplePassword = simplePassword;
    }

    public void updateCreditCard(CreditCardInfo creditCardInfo) {
        this.cardNickname = creditCardInfo.cardNickname;
        this.cardNum = creditCardInfo.cardNum;
        this.expireYear = creditCardInfo.expireYear;
        this.expireMonth = creditCardInfo.expireMonth;
        this.simplePassword = creditCardInfo.simplePassword;
    }
}
