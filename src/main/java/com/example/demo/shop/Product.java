package com.example.demo.shop;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String categoryName;

    private String imageUrl;

    private String alarmUrl;

    private int price;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<String> tags = new ArrayList<>();

    @Builder
    public Product(Long id, int price, String name, String categoryName, String imageUrl, String alarmUrl) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.alarmUrl = alarmUrl;
        this.price = price;
    }

    @Builder
    public Product(String name, String categoryName, String imageUrl, String alarmUrl) {
        this.name = name;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.alarmUrl = alarmUrl;
    }

    public void update(String name, String categoryName, String imageUrl, String alarmUrl, int price) {
        this.name = name;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.alarmUrl = alarmUrl;
        this.price = price;
    }
}
