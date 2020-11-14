package com.example.demo.customProduct;

import com.example.demo.customOrderProduct.CustomOrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_product_id")
    private Long id;

    private String productName;
    private String author;

    private int price;

    private int price;

    @OneToMany(mappedBy = "customProduct", cascade = CascadeType.ALL)
    private List<CustomOrderProduct> customOrderProducts = new ArrayList<>();
}
