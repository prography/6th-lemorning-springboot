package com.example.demo.user;

import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.exception.NotEnoughPointException;
import com.example.demo.order.Order;
import com.example.demo.point.Point;
import com.example.demo.upload.Upload;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"email", "password", "birthday"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "auth")
    private String auth;

    private LocalDate birthday;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String nickName;

    private int point;

    // User - Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    // User - Upload
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Upload> uploads = new ArrayList<>();

    // User - CreditCardInfo
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CreditCardInfo> creditCardList = new ArrayList<>();

    @Builder
    public User(String email, String password, String auth, LocalDate birthday, Gender gender, String nickName) {
        this.email = email;
        this.password = password;
        this.auth = auth;
        this.birthday = birthday;
        this.gender = gender;
        this.nickName = nickName;
        this.point = 0;
    }

    public void addPoint(int point) {
        this.point += point;
    }


    public void removePoint(int point) {
        int result = this.point - point;

        if (result < 0)
            throw new NotEnoughPointException("포인트가 부족합니다!");

        this.point = result;
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}