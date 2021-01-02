package com.example.demo.creditcard;

import com.example.demo.request.UpdateCardRequestDto;
import com.example.demo.point.Point;
import com.example.demo.point.PointRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreditCardService {

    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;
    private final PointRepository pointRepository;

    @Transactional
    public Long save(CreditCardInfo creditCardInfo) {
        creditCardRepository.save(creditCardInfo);

        return creditCardInfo.getId();
    }

    public List<CreditCardInfo> findAll() {
        return creditCardRepository.findAll();
    }

    public List<CreditCardInfo> findUserCardList(Long userId) {
        return creditCardRepository.findByUser(userId);
    }

    public CreditCardInfo findOne(Long id) {
        return creditCardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Long chargePoint(String userMail, String cardNum, int point) {
        User user = userRepository.findByEmail(userMail).orElseThrow(EntityNotFoundException::new);
        CreditCardInfo card = creditCardRepository.findBycardNum(cardNum).orElseThrow(EntityNotFoundException::new);

        user.addPoint(point);
        Point p = Point.createPoint(card, point);

        Point savePoint = pointRepository.save(p);

        return savePoint.getId();
    }

    @Transactional
    public Long update(Long id, UpdateCardRequestDto updateCardInfo) {
        CreditCardInfo findCreditCard = creditCardRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        findCreditCard.updateCreditCard(updateCardInfo);

        return findCreditCard.getId();
    }

    @Transactional
    public Long delete(Long cardId) {
        creditCardRepository.deleteById(cardId);

        return cardId;
    }
}
