package com.example.demo.shop;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String categoryName;

    private String imageUrl;

    private String alarmUrl;

    private int price;

    @JsonIgnore
    // 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<String> tags = new ArrayList<>();

    @Builder
    public Product(String name, String categoryName, String imageUrl, String alarmUrl, int price, User user) {
        this.name = name;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.alarmUrl = alarmUrl;
        this.price = price;
        this.user = user;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }


    public void update(String name, String categoryName, String imageUrl, String alarmUrl, int price) {
        this.name = name;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.alarmUrl = alarmUrl;
        this.price = price;
    }
    public void update(Product product) {
        this.name = product.getName();
        this.categoryName = product.getCategoryName();
        this.imageUrl = product.getImageUrl();
        this.alarmUrl = product.getAlarmUrl();
        this.price = product.getPrice();
    }

    // 구매 물품을 등록한다.
    // 'N' 쪽에서 처리해준다.
    public void addBuyingProductList(User user){
        this.user = user;
        user.getBuyingProducts().add(this);
    }

    // 판매 물품을 등록한다.
    public void addSellingProductList(User user){
        this.user = user;
        user.getSellingProducts().add(this);
    }

    public static Product buy(User user, Product product){
        Product new_product = new Product();
        new_product.update(product);
        new_product.addBuyingProductList(user);
        user.setPointSum(user.getPointSum()-new_product.getPrice());
        return new_product;
    }
    public static Product addProduct(User user, ProductDto dto) {
        Product new_product = Product.toEntity(dto);
        new_product.addSellingProductList(user);
        return  new_product;
    }

    private static Product toEntity(ProductDto dto) {
        return Product.builder()
                        .name(dto.getName())
                        .categoryName(dto.getCategoryName())
                        .imageUrl(dto.getImageUrl())
                        .alarmUrl(dto.getAlarmUrl())
                        .price(dto.getPrice())
                .build();
    }
}
