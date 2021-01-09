package com.lemorning.demo.creditcard.request;

import com.lemorning.demo.creditcard.CreditCardBank;
import com.lemorning.demo.creditcard.CreditCardInfo;
import com.lemorning.demo.customOrder.CustomOrder;
import com.lemorning.demo.order.Order;
import com.lemorning.demo.user.User;
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
    private User user;
    private List<CustomOrder> customOrders = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

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
                .user(dto.getUser())
                .customOrders(dto.getCustomOrders())
                .orders(dto.getOrders())
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
