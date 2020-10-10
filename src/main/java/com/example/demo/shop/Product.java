package com.example.demo.shop;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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


}
