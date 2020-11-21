package com.example.demo.product;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Product findByName(String name) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return productRepository.findByproductName(name);
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void updateProduct(Long id, Product product) {
        Product p = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        p.update(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

//    @Transactional
//    public List<Product> findSellingListByUserId(Long id) {
//        List<Product> allProducts = productRepository.findAll();
//        List<Product> answer = new ArrayList<>();
//        for(Product product : allProducts){
//            if(product.getUser().getId()==id){
//                answer.add(product);
//            }
//        }
//        return answer;
//    }
//    @Transactional
//    public List<Product> findBuyingListByUserId(Long id) {
//        List<Product> allProducts = productRepository.findAll();
//        List<Product> answer = new ArrayList<>();
//        for(Product product : allProducts){
//            if(product.getUser().getId()==id){
//                answer.add(product);
//            }
//        }
//        return answer;
//    }
//    @Transactional
//    public Product addSellingProduct(ProductDto dto, String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
//
//        Product new_product = Product.addProduct(user,dto);
//        productRepository.save(new_product);
//        return new_product;
//    }
//
//    @Transactional
//    public Product buy(String email, Long productId){
//	    // 1. 조회
//	    User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
//	    Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
//
//	    // 2. 잔고 확인
//        buyAvailable(email,product.getPrice());
//
//        // 3. 구매
//	    Product new_product = Product.buy(user,product);
//
//	    // 4. 저장 ( 저장 해야 유저도 반영이 되었음 )
//	    productRepository.save(new_product);
//	    return new_product;
//    }

//    /**
//     * 잔고 확인
//     * @param email
//     * @param price
//     */
//    @Transactional
//    public void buyAvailable(String email, int price) {
//        User findUser = findByEmail(email);
//        if(findUser.getPointSum()-price<0){
//            throw new IllegalStateException("돈이 부족합니다.");
//        }
//    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return user;
    }


}