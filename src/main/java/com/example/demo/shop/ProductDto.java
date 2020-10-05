package com.example.demo.shop;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
  private String name;

  private String categoryName;

  private String imageUrl;

  private String alarmUrl;

  private int price;

}