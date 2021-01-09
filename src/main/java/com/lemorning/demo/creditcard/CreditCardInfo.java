package com.lemorning.demo.creditcard;

import com.lemorning.demo.account.Account;
import com.lemorning.demo.customOrder.CustomOrder;
import com.lemorning.demo.order.Order;
import com.lemorning.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"user"})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;            // 계정

//    private String billingKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateUser(User user){
        this.user = user;
        if (!user.getCreditCardInfos().contains(this)) {
            user.getCreditCardInfos().add(this);
        }
    }

    @OneToMany(mappedBy = "creditCardInfo", cascade = CascadeType.ALL)
    private List<CustomOrder> customOrders = new ArrayList<>();

    @OneToMany(mappedBy = "creditCardInfo", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public void updateCreditCard(CreditCardInfo creditCardInfo) {
        this.cardNickname = creditCardInfo.cardNickname;
        this.cardNum = creditCardInfo.cardNum;
        this.expireYear = creditCardInfo.expireYear;
        this.expireMonth = creditCardInfo.expireMonth;
        this.simplePassword = creditCardInfo.simplePassword;
    }
}
