package com.example.demo.customOrder;

import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.customOrderProduct.CustomOrderProduct;
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
}
