package com.example.demo.shop;

import com.example.demo.user.User;
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

  private Long seller_id;

  public static ProductDto toDto(Product findProduct, Long user_id) {
    ProductDto answerDto = new ProductDto();
    answerDto.setImageUrl(findProduct.getImageUrl());
    answerDto.setAlarmUrl(findProduct.getAlarmUrl());
    answerDto.setCategoryName(findProduct.getCategoryName());
    answerDto.setPrice(findProduct.getPrice());
    answerDto.setName(findProduct.getName());
    answerDto.setSeller_id(user_id);
    return  answerDto;
  }
}