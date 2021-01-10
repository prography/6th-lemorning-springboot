package com.example.demo.creditcard.response;

import com.example.demo.creditcard.CreditCardInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardPublicDto {

    private Long id;
    private String bankName;
    private String cardNum;


    public static CardPublicDto toDto(CreditCardInfo card) {
        return CardPublicDto.builder()
                .id(card.getId())
                .bankName(card.getCreditCardBank().name())
                .cardNum(card.getCardNum())
                .build();
    }
}
