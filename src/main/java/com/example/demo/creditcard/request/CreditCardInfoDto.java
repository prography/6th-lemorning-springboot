package com.example.demo.creditcard.request;

import com.example.demo.creditcard.CreditCardBank;
import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.customOrder.CustomOrder;
import com.example.demo.order.Order;
import com.example.demo.user.User;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardInfoDto {

    private CreditCardBank creditCardBank;
    private String cardNickname;
    private String cardNum;
    private int expireYear;
    private int expireMonth;
    private LocalDate birth;
    private String simplePassword;
    private String email;

    /**
     * 신용카드 등록을 위한 dto to entity
     * @Auther 유동관
     * @Date 21/01/03
     * @param dto
     * @return CreditCardInfo Entity
     */
    public static CreditCardInfo toEntity(CreditCardInfoDto dto) {
        return CreditCardInfo.builder()
                .cardNickname(dto.getCardNickname())
                .creditCardBank(dto.getCreditCardBank())
                .cardNum(dto.getCardNum())
                .expireYear(dto.getExpireYear())
                .expireMonth(dto.getExpireMonth())
                .birth(dto.getBirth())
                .simplePassword(dto.getSimplePassword())
                .build();
    }

    public static CreditCardInfoDto toDto(CreditCardInfo entity) {
        return CreditCardInfoDto.builder()
                .cardNickname(entity.getCardNickname())
                .creditCardBank(entity.getCreditCardBank())
                .cardNum(entity.getCardNum())
                .expireYear(entity.getExpireYear())
                .expireMonth(entity.getExpireMonth())
                .birth(entity.getBirth())
                .simplePassword(entity.getSimplePassword())
                .email(entity.getUser().getEmail())
                .build();
    }


    public void updateCreditCard(CreditCardInfoDto creditCardInfo) {
        this.cardNickname = creditCardInfo.cardNickname;
        this.cardNum = creditCardInfo.cardNum;
        this.expireYear = creditCardInfo.expireYear;
        this.expireMonth = creditCardInfo.expireMonth;
        this.simplePassword = creditCardInfo.simplePassword;
    }
}
