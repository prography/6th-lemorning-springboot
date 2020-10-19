package com.example.demo.creditcard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Transactional
    public void save(CreditCardInfo creditCardInfo) {
        creditCardRepository.save(creditCardInfo);
    }

    public List<CreditCardInfo> findAll() {
        return creditCardRepository.findAll();
    }

    public CreditCardInfo findOne(Long id) {
        return creditCardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Long update(Long cardId, CreditCardInfo creditCardInfo) {
        CreditCardInfo findCreditCard = creditCardRepository.findById(cardId).orElseThrow();

        findCreditCard.update(creditCardInfo);

        return findCreditCard.getId();
    }

    @Transactional
    public Long delete(Long cardId) {
        creditCardRepository.deleteById(cardId);

        return cardId;
    }
}
