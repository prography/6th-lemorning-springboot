package com.example.demo.request;

import lombok.Getter;

@Getter
public class ChargePointRequestDto {
    private String email;
    private String cardNum;
    private String simplePassword;
}
