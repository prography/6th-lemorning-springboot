package com.lemorning.demo.creditcard.response;

import com.lemorning.demo.creditcard.CreditCardInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedCardRes {

    private String cardNickname;
    private String email;

    public static SavedCardRes toDto(CreditCardInfo savedCard) {
        return SavedCardRes.builder()
                .cardNickname(savedCard.getCardNickname())
                .email(savedCard.getUser().getEmail())
                .build();
    }
}
