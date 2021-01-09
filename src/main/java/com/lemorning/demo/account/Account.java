package com.lemorning.demo.account;

import com.lemorning.demo.creditcard.CreditCardInfo;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CreditCardInfo> creditCardInfos = new ArrayList<>();
}
