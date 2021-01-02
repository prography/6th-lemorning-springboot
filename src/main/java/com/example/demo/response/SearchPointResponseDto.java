package com.example.demo.response;

import com.example.demo.creditcard.CreditCardBank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchPointResponseDto {
    private int point;
    private CreditCardBank cardBank;
    private String cardNickName;
    private LocalDateTime chargeDateTime;
}
