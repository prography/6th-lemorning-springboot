package com.example.demo.shop;

import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product findByName(String name) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return productRepository.findByName(name);
    }

    @Transactional
    public Long save(ProductDto infoDto) {
        return productRepository.save(Product.builder()
                .name(infoDto.getName())
                .categoryName(infoDto.getCategoryName())
                .imageUrl(infoDto.getImageUrl())
                .alarmUrl(infoDto.getAlarmUrl())
                .price(infoDto.getPrice())
                .build()).getId();
    }

    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Long update(Long id, ProductDto infoDto) {
        Product p = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        p.update(infoDto.getName(), infoDto
                .getCategoryName(), infoDto.getImageUrl(), infoDto.getAlarmUrl(), infoDto.getPrice());

        return id;
    }

    @Transactional
    public Long delete(Long id) {
        productRepository.deleteById(id);

        return id;
    }
}