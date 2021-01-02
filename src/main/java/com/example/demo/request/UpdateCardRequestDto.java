package com.example.demo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCardRequestDto {
    private String cardNickName;
    private String simplePassword;
}
