package com.example.demo.upload;

import com.example.demo.product.CustomProduct;
import com.example.demo.product.GeneralProduct;
import com.example.demo.product.ProductService;
import com.example.demo.uploadproduct.UploadProduct;
import com.example.demo.user.JwtUserDetailsService;
import com.example.demo.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class UploadServiceTest {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private JwtUserDetailsService userService;
    @Autowired
    private ProductService productService;

    @Test
    public void UploadTest() throws Exception {
        // 회원가입
        User user = User.builder()
                .email("ssssssss@naver.com")
                .birthday(LocalDate.of(2020, 12, 20))
                .build();
        userService.save(user);

        // 주문 상품 생성
        GeneralProduct gp = new GeneralProduct();
        gp.setAuthor("gp1");
        gp.setCategoryName("General");
        gp.setPrice(10000);
        gp.setProductName("gpgpgpgpgpgpg");
        gp.setImageUrl("Wwwwwwwwwww");
        productService.save(gp);

        CustomProduct cp = new CustomProduct();
        cp.setAuthor("cp1");
        cp.setCategoryName("Custom");
        cp.setPrice(80000);
        cp.setProductName("cpcpcpccpcppccsp");
        cp.setAlarmUrl("ffffffffff");
        productService.save(cp);

        Long uploadId = uploadService.upload(user.getEmail(), gp.getId(), cp.getId());

        uploadService.findUploadById(uploadId);
    }
}