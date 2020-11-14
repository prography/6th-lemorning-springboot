package com.example.demo.customOrderProduct;

import com.example.demo.customOrder.CustomOrder;
import com.example.demo.customProduct.CustomProduct;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomOrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_product_id")
    private CustomProduct customProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_order_id")
    private CustomOrder customOrder;

    private int customOrderPrice;

<<<<<<< HEAD
    // 생성 메소드
    public static CustomOrderProduct createCustomOrderProduct(CustomProduct customProduct, int price) {
        CustomOrderProduct customOrderProduct = new CustomOrderProduct();
        customOrderProduct.setCustomProduct(customProduct);
        customOrderProduct.setCustomOrderPrice(price);
=======
    public static CustomOrderProduct createCustomOrderProduct(CustomProduct customProduct) {
        CustomOrderProduct customOrderProduct = new CustomOrderProduct();
        customOrderProduct.setCustomProduct(customProduct);
        customOrderProduct.setCustomOrderPrice(customProduct.getPrice());
>>>>>>> add Service & Repository

        return customOrderProduct;
    }
}
