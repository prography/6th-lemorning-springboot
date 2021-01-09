package com.lemorning.demo.customOrder;

import com.lemorning.demo.creditcard.CreditCardInfo;
import com.lemorning.demo.customOrderProduct.CustomOrderProduct;
import com.lemorning.demo.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_order_id")
    private Long id;

    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id")
    private CreditCardInfo creditCardInfo;

    @OneToMany(mappedBy = "customOrder", cascade = CascadeType.ALL)
    private List<CustomOrderProduct> customOrderProducts = new ArrayList<>();

    // 생성 메소드
    public static CustomOrder createCustomOrder(User user, CreditCardInfo creditCardInfo, CustomOrderProduct... customOrderProducts) {
        CustomOrder customOrder = new CustomOrder();
        customOrder.addUser(user);
        customOrder.addCreditCardInfo(creditCardInfo);
        customOrder.setOrderDate(LocalDate.now());

        for (CustomOrderProduct customOrderProduct : customOrderProducts)
            customOrder.addCustomOrderProduct(customOrderProduct);

        return customOrder;
    }

    // 연관관계 메소드
    public void addUser(User user) {
        this.user = user;
        user.getCustomOrders().add(this);
    }

    public void addCreditCardInfo(CreditCardInfo creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
        creditCardInfo.getCustomOrders().add(this);
    }

    public void addCustomOrderProduct(CustomOrderProduct customOrderProduct) {
        this.customOrderProducts.add(customOrderProduct);
        customOrderProduct.setCustomOrder(this);
    }
}
