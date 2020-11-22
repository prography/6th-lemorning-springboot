package com.example.demo.uploadproduct;

import com.example.demo.product.Product;
import com.example.demo.upload.Upload;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "upload_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_id")
    private Upload upload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public static UploadProduct createUploadProduct(Product product) {
        UploadProduct uploadProduct = new UploadProduct();
        uploadProduct.setProduct(product);

        return uploadProduct;
    }
}
