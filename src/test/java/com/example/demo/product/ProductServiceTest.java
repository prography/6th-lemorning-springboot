package com.example.demo.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Test
    void saveProduct() {
        ProductDto productDto = ProductDto.builder()
                .name("우왁굳모닝콜").categoryName("유머")
                .imageUrl("http://www.naver.com")
                .alarmUrl("http://www.naver.com")
                .build();
        productService.save(productDto);
    }
}