package com.example.demo.customOrder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOrderService {

    private final CustomOrderRepository customOrderRepository;

    @Transactional
    public Long save(CustomOrder customOrder) {
        customOrderRepository.save(customOrder);

        return customOrder.getId();
    }

    public CustomOrder findById(Long id) {
        return customOrderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<CustomOrder> findAll() {
        return customOrderRepository.findAll();
    }
}
