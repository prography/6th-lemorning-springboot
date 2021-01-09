package com.example.demo.creditcard;

import com.example.demo.creditcard.request.CreditCardInfoDto;
import com.example.demo.config.JwtUserDetailsService;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final JwtUserDetailsService userService;

    @Transactional
    public CreditCardInfo save(CreditCardInfoDto dto, Principal principal) {
        CreditCardInfo creditCardInfo = CreditCardInfoDto.toEntity(dto);    // 엔티티로 변환하는 작업 해줌 - 21/01/03 유동관
        User byEmail = userService.findByEmail(principal.getName());
        creditCardInfo.updateUser(byEmail);

        return creditCardRepository.save(creditCardInfo);
    }

    public List<CreditCardInfo> findAll() {
        return creditCardRepository.findAll();
    }

    public CreditCardInfo findOne(Long id) {
        return creditCardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void update(Long id, CreditCardInfo creditCardInfo) {
        CreditCardInfo findCreditCard = creditCardRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        findCreditCard.updateCreditCard(creditCardInfo);
    }

    @Transactional
    public void delete(long cardId,Principal principal) {
        User byEmail = userService.findByEmail(principal.getName());
        CreditCardInfo card = creditCardRepository.findById(cardId);
        byEmail.deleteCard(card);
        creditCardRepository.deleteById(cardId);
    }
}
