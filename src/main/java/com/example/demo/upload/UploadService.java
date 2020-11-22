package com.example.demo.upload;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.uploadproduct.UploadProduct;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadService {

    private final UserRepository userRepository;
    private final UploadRepository uploadRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long upload(String email, Long... productIds) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

        List<UploadProduct> uploadProducts = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
            uploadProducts.add(UploadProduct.createUploadProduct(product));
        }

        // 업로드 생성
        Upload upload = Upload.createUpload(user, uploadProducts);

        // 업로드 저장
        uploadRepository.save(upload);

        return upload.getId();
    }

    public Upload findUploadById(Long uploadId) {
        return uploadRepository.findById(uploadId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Upload> findAllUpload() {
        return uploadRepository.findAll();
    }
}
