package com.example.demo.point;

import com.example.demo.creditcard.CreditCardInfo;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"point", "chargeDateTime"})
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    private int point;

    private LocalDateTime chargeDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id")
    private CreditCardInfo creditCard;

    public static Point createPoint(CreditCardInfo card, int point) {
        Point p = new Point();
        p.point = point;
        p.chargeDateTime = LocalDateTime.now();
        p.setCreditCard(card);

        return p;
    }

    public void setCreditCard(CreditCardInfo card) {
        card.getPointHistory().add(this);
        this.creditCard = card;
    }
}
