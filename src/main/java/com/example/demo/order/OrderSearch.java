package com.example.demo.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String email;      // 회원 이름
    private OrderStatus orderStatus; // 주문 상태 [ORDER,CANCEL]
}
