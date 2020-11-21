package com.example.demo.order;

import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.orderProduct.OrderProduct;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDateTime;    // 주문 시간

    @JsonIgnore
    // 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 반대편 변수의 이름을 mappedBy에 적는다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addOrderItem(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    // 생성 메서드
    // 포인트 차감
    public static Order createOrder(User user, List<OrderProduct> orderProducts) {
        Order order = new Order();
        order.setUser(user);

        int totalprice = 0;
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderItem(orderProduct);
            totalprice += orderProduct.getPrice();
        }

        user.removePoint(totalprice);

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDateTime(LocalDateTime.now());

        return order;
    }

    public void cancelOrder() {
        this.setStatus(OrderStatus.CANCEL);
    }
}
