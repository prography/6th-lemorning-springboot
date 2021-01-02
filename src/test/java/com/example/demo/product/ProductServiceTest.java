package com.example.demo.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void saveProductTest() throws Exception {
        // given
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
        cp.setPrice(100000000);
        cp.setProductName("cpcpcpccpcppccsp");
        cp.setAlarmUrl("ffffffffff");
        productService.save(cp);

        // when
        List<Product> all = productService.findAll();
        for (Product product : all)
            System.out.println(product.toString());

        // then
    }
}