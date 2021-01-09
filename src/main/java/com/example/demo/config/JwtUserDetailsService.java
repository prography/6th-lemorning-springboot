package com.example.demo.config;

import com.example.demo.user.Role;
import com.example.demo.product.Product;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        if (email.equals("dkyou7@naver.com")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }

        return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }

    public User authenticateByEmailAndPassword(String email, String password) {
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("Password not matched");
        }

        return member;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return user;
    }

    public List<Product> findSellingList(String email) {
        User findUser = findByEmail(email);
        return findUser.getSellingProducts();
    }

    public List<Product> findBuyingList(String email) {
        User findUser = findByEmail(email);
        return findUser.getBuyingProducts();
    }

    public void updatePointInfo(long l, int pointAmount) {
        User user = userRepository.findById(l).orElseThrow();
        user.setPointSum(user.getPointSum()+pointAmount);
    }
    /**
     * 회원정보 저장
     *
     * @param user 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
