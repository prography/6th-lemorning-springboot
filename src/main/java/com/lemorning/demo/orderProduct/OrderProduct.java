package com.lemorning.demo.orderProduct;

import com.lemorning.demo.order.Order;
import com.lemorning.demo.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성자를 다른 계층에서 함부로 생성하지 못하도록 막아준다.
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;     // 주문 가격

    // 생성 메서드
    // 먼저 product 랑 orderItem 이랑 묶어준다.
    public static OrderProduct createOrderProduct(Product product){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrderPrice(product.getPrice());

        return orderProduct;
    }
}
