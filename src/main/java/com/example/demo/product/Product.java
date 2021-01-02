package com.example.demo.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorColumn(name = "ptype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String productName;
    private String author;
    private String categoryName;
    private String imageUrl;
    private String alarmUrl;
    private int price;

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private List<String> tags = new ArrayList<>();

    public void update(Product p) {
        this.productName = p.getProductName();
        this.categoryName = p.getCategoryName();
        this.imageUrl = p.getImageUrl();
        this.alarmUrl = p.getAlarmUrl();
        this.price = p.getPrice();
    }
}
