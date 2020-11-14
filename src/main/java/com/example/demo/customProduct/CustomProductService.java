package com.example.demo.customProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomProductService {

    private final CustomProductRepository customProductRepository;

    @Transactional
    public Long save(CustomProduct customProduct) {
        customProductRepository.save(customProduct);

        return customProduct.getId();
    }

    public CustomProduct findById(Long id) {
        return customProductRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<CustomProduct> findAll() {
        return customProductRepository.findAll();
    }
}
